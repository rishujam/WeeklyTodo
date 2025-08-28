package com.weekly.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.weekly.todo.ui.ScreenData
import com.weekly.todo.ui.UIEvent
import com.weekly.todo.ui.screens.HabitCreationScreen
import com.weekly.todo.ui.screens.HabitScreen
import com.weekly.todo.ui.screens.HomeScreen

@Composable
fun NavigationStack(
    modifier: Modifier,
    screenData: ScreenData,
    onEvent: (UIEvent) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController, modifier = modifier, screenData = screenData)
        }
        composable(
            route = Screen.Habit.route + "?habitId={habitId}",
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            HabitScreen(habitId = it.arguments?.getInt("habitId"), screenData, modifier)
        }
        composable(route = Screen.HabitCreation.route) {
            HabitCreationScreen(modifier, navController) {
                onEvent(UIEvent.NewHabitCreated(it))
            }
        }
    }
}