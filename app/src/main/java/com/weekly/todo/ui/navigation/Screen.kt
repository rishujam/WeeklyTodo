package com.weekly.todo.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Habit : Screen("habit_screen")
    data object HabitCreation : Screen("habit_creation_screen")
}