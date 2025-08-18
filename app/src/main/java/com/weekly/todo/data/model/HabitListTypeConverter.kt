package com.weekly.todo.data.model

import androidx.room.TypeConverter

class HabitListTypeConverter {

    @TypeConverter
    fun fromHabitList(habits: List<Habit>?): String {
        if (habits.isNullOrEmpty()) return ""
        return habits.joinToString(separator = ";") { habit ->
            listOf(
                habit.id,
                habit.title.replace(";", "\\;"), // escape separator if present
                habit.maxWeight.toString(),
                habit.progress.toString(),
                habit.dateCreated.toString()
            ).joinToString(separator = "|")
        }
    }

    @TypeConverter
    fun toHabitList(data: String): List<Habit> {
        if (data.isBlank()) return emptyList()
        return data.split(";").map { habitStr ->
            val parts = habitStr.split("|")
            Habit(
                id = parts[0],
                title = parts[1].replace("\\;", ";"),
                maxWeight = parts[2].toInt(),
                progress = parts[3].toInt(),
                dateCreated = parts[4].toLong()
            )
        }
    }

}