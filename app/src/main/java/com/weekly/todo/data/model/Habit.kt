package com.weekly.todo.data.model

data class Habit(
    val id: String,
    val title: String,
    val maxWeight: Int,
    val progress: Int,
    val dateCreated: Long = System.currentTimeMillis()
)