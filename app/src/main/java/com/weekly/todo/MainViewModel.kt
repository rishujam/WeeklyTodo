package com.weekly.todo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weekly.todo.data.model.Habit
import com.weekly.todo.data.model.Week
import com.weekly.todo.data.repo.WeekRepository
import com.weekly.todo.ui.ScreenData
import com.weekly.todo.ui.UIEvent
import com.weekly.todo.util.Constants.DEBUG_LOG_TAG
import com.weekly.todo.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repo: WeekRepository
) : ViewModel() {

    var isAppInitialisationInProgress = true

    init {
        onAppStart()
    }

    var state by mutableStateOf(ScreenData(ResultState.Loading()))

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.NewHabitCreated -> {
                addNewHabit(newHabit = event.habit)
            }
            is UIEvent.HabitProgressUpdate -> {
                updateHabit(event.updatedHabit, event.week)
            }
            is UIEvent.DeleteHabit -> {
                deleteHabit(event.habit.id, event.week)
            }
        }
    }

    private fun addNewHabit(newHabit: Habit) = viewModelScope.launch {
        state.weeks.data?.last()?.let { currentWeek ->
            state = state.copy(
                weeks = ResultState.Loading()
            )
            val id = if(currentWeek.habits.isEmpty()) 1 else {
                currentWeek.habits.last().id + 1
            }
            val habit = newHabit.copy(
                id = id
            )
            val currentWeekId = currentWeek.weekRange
            val updatedHabits = currentWeek.habits.toMutableList()
            updatedHabits.add(habit)
            repo.updateHabits(currentWeekId, updatedHabits)
            state = state.copy(weeks = ResultState.Loading())
            val weeks = repo.getWeeks()
            state = state.copy(
                weeks = ResultState.Success(weeks)
            )
        } ?: run {
            state = state.copy(
                weeks = ResultState.Error("Unknown Error")
            )
        }
    }

    private fun onAppStart() = viewModelScope.launch {
        val weeks = repo.getWeeks()
        if(weeks.isEmpty()) {
            // Create a empty week object and upsert it to DB
            val currWeek = Week(
                weekRange = getCurrentWeekRange(),
                weekNo = 1,
                emptyList()
            )
            repo.addWeek(currWeek)
            Log.d(DEBUG_LOG_TAG, "new first week added")
        } else {
            val currentWeekRange = getCurrentWeekRange()
            val lastWeek = weeks.last()
            if(lastWeek.weekRange != currentWeekRange) {
                val missingWeeks = generateMissingWeeks(
                    lastWeek.weekRange,
                    lastWeek.weekNo,
                    lastWeek.habits
                )
                repo.addWeeks(missingWeeks)
                Log.d(DEBUG_LOG_TAG, "adding missing weeks in DB: ")
            }
        }
        val updatedWeeks = repo.getWeeks()
        isAppInitialisationInProgress = false
        state = state.copy(weeks = ResultState.Success(updatedWeeks))
    }

    private fun getCurrentWeekRange(): String {
        val today = LocalDate.now()
        val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return "${monday.format(formatter)} - ${sunday.format(formatter)}"
    }

    private fun generateMissingWeeks(
        lastWeekRange: String,
        lastWeekNo: Int,
        lastRecordedHabits: List<Habit>
    ): List<Week> {
        val habits = lastRecordedHabits.map {
            it.progress = 0
            it
        }
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        val parts = lastWeekRange.split(" - ")
        require(parts.size == 2) { "Invalid week range format: $lastWeekRange" }
        val lastStart = LocalDate.parse(parts[0].trim(), formatter)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) // normalize to Monday
        val nextWeekStart = lastStart.plusWeeks(1)
        val today = LocalDate.now()
        val currentMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weeks = mutableListOf<Week>()
        var weekStart = nextWeekStart
        var weekNo = lastWeekNo + 1
        while (!weekStart.isAfter(currentMonday)) {
            val weekEnd = weekStart.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            val weekRange = "${weekStart.format(formatter)} - ${weekEnd.format(formatter)}"
            weeks.add(
                Week(
                    weekNo = weekNo,
                    weekRange = weekRange,
                    habits = habits
                )
            )
            weekStart = weekStart.plusWeeks(1)
            weekNo++
        }
        return weeks
    }

    private fun updateHabit(updatedHabit: Habit, week: Week) = viewModelScope.launch {
        val updatedHabits = week.habits.toMutableList()
        val index = updatedHabits.indexOfFirst { it.id == updatedHabit.id }
        if (index != -1) {
            updatedHabits[index] = updatedHabit
            repo.updateHabits(week.weekRange, updatedHabits)
        } else {
            state = state.copy(
                weeks = ResultState.Error("Cannot Update Habit: Habit with id=${updatedHabit.id} not found in week ${week.weekRange}")
            )
        }
    }

    private fun deleteHabit(habitId: Int, week: Week) = viewModelScope.launch {
        val updatedHabits = week.habits.toMutableList()
        val index = updatedHabits.indexOfFirst { it.id == habitId }
        if (index != -1) {
            updatedHabits.removeAt(index)
            repo.updateHabits(week.weekRange, updatedHabits)
            val weeks = repo.getWeeks()
            state = state.copy(
                weeks = ResultState.Success(weeks)
            )
        } else {
            state = state.copy(
                weeks = ResultState.Error("Error deleting habit.")
            )
        }
    }

}