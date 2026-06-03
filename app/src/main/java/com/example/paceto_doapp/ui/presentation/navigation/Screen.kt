package com.example.paceto_doapp.ui.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Calendar : Screen("calendar")
    object TaskListScreen : Screen("task_list_screen")
    object TaskFormScreen : Screen("task_form") {
        fun passId(taskId: String? = null): String {
            return if (taskId != null) "task_form?taskId=$taskId" else "task_form"
        }
    }
}