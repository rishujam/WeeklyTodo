package com.weekly.todo.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.weekly.todo.data.model.Habit
import com.weekly.todo.data.model.Week

@Dao
interface WeekDao {

    @Upsert
    suspend fun upsert(weeks: List<Week>)

    @Upsert
    suspend fun upsert(week: Week)

    @Query("SELECT * FROM weeks")
    suspend fun getWeeks(): List<Week>

    @Query("UPDATE weeks SET habits = :habits WHERE weekRange = :weekRange")
    suspend fun updateHabits(weekRange: String, habits: List<Habit>)

}