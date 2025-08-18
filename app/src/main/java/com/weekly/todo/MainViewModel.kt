package com.weekly.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weekly.todo.data.model.Week
import com.weekly.todo.data.repo.WeekRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repo: WeekRepository
) : ViewModel() {

    var isAppInitialisationInProgress = true

    init {
        onAppStart()
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
            Log.d("RishuTest", "new week added")
            isAppInitialisationInProgress = false
        } else {
            val currentWeekRange = getCurrentWeekRange()
            if(weeks.last().weekRange != currentWeekRange) {
                //TODO Create missing week objects and upsert it to DB using last week habits.
                Log.d("RishuTest", "adding missing weeks in DB")
            }
            isAppInitialisationInProgress = false
        }
    }



    private fun getCurrentWeekRange(): String {
        val today = LocalDate.now()
        val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return "${monday.format(formatter)} - ${sunday.format(formatter)}"
    }

}