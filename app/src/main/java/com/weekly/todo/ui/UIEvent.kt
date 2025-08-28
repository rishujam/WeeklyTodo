package com.weekly.todo.ui

import com.weekly.todo.data.model.Habit

sealed class UIEvent {
    data class NewHabitCreated(val habit: Habit) : UIEvent()
}
