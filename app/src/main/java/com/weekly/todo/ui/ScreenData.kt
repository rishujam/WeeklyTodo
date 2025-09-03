package com.weekly.todo.ui

import com.weekly.todo.data.model.Week
import com.weekly.todo.util.ResultState

data class ScreenData(
    val weeks: List<Week>,
    val errorState: String? = null,
    val isLoading: Boolean = false,
    val uiEffect: UIEffect? = null
)
