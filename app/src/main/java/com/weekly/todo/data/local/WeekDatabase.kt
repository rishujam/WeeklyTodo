package com.weekly.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weekly.todo.data.model.Week

@Database(
    entities = [Week::class],
    version = 1
)
abstract class WeekDatabase : RoomDatabase() {

    abstract fun weekDao(): WeekDao

}