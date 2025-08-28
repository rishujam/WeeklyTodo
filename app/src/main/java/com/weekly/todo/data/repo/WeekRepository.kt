package com.weekly.todo.data.repo

import com.weekly.todo.data.model.Habit
import com.weekly.todo.data.model.Week

interface WeekRepository {

    suspend fun addWeeks(weeks: List<Week>)

    suspend fun addWeek(week: Week)

    suspend fun getWeeks(): List<Week>

    suspend fun updateHabits(weekRange: String, habits: List<Habit>)

}