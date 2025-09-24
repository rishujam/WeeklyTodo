package com.weekly.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform