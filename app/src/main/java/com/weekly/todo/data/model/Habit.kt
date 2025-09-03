package com.weekly.todo.data.model

data class Habit(
    val id: Int,
    val title: String,
    val maxWeight: Int,
    var progress: Int = 0,
    val dateCreated: Long = System.currentTimeMillis()
)