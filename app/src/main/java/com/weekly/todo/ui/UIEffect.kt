package com.weekly.todo.ui

sealed class UIEffect {
    data class ShowToast(val message: String) : UIEffect()
//    data class Navigate(val route: String) : UIEffect()
//    object NavigateBack : UIEffect()

}
