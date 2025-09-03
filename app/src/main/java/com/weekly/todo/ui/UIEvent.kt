package com.weekly.todo.ui

import com.weekly.todo.data.model.Habit
import com.weekly.todo.data.model.Week

sealed class UIEvent {
    data class NewHabitCreated(val habit: Habit) : UIEvent()
    data class HabitProgressUpdate(
        val updatedHabit: Habit,
        val week: Week
    ) : UIEvent()
    data class DeleteHabit(val habit: Habit, val week: Week) : UIEvent()
    data object ClearUiEffect : UIEvent()
}
