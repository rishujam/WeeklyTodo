package com.weekly.todo

object SharedLogger {
    fun d(tag: String, message: String) {
        println("DEBUG [$tag]: $message")
    }
    
    fun i(tag: String, message: String) {
        println("INFO [$tag]: $message")
    }
    
    fun e(tag: String, message: String) {
        println("ERROR [$tag]: $message")
    }
}