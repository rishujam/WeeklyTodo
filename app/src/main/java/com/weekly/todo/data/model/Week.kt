package com.weekly.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weeks")
@TypeConverters(HabitListTypeConverter::class)
data class Week(
    @PrimaryKey val weekRange: String,
    val weekNo: Int,
    val habits: List<Habit>
)
