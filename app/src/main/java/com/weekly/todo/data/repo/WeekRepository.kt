package com.weekly.todo.data.repo

import com.weekly.todo.data.model.Week

interface WeekRepository {

    suspend fun addWeek(week: Week)

    suspend fun getWeeks(): List<Week>

}