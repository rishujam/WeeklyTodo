package com.weekly.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weekly.todo.data.model.HabitListTypeConverter
import com.weekly.todo.data.model.Week

@Database(
    entities = [Week::class],
    version = 1
)
@TypeConverters(HabitListTypeConverter::class)
abstract class WeekDatabase : RoomDatabase() {

    abstract fun weekDao(): WeekDao

}