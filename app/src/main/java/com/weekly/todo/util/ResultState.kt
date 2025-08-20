package com.weekly.todo.util

sealed class ResultState<T>(val data: T? = null, val message: String? = null) {

    class Error<T>(message: String?) : ResultState<T>(message = message)

    class Success<T>(data: T) : ResultState<T>(data = data)

    class Loading<T> : ResultState<T>()

}