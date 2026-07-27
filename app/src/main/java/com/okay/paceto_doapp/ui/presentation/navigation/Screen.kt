package com.okay.paceto_doapp.ui.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Calendar : Screen("calendar")
    object FocusList : Screen("focus_list")
    object TaskListScreen : Screen("task_list_screen")
    object ProfileScreen : Screen("profile")
    object SettingsScreen : Screen("settings")
    object TaskFormScreen : Screen("task_form") {
        fun passId(taskId: String? = null): String {
            return if (taskId != null) "task_form?taskId=$taskId" else "task_form"
        }
    }
    object PomodoroScreen : Screen("pomodoro") {
        fun passId(focusName: String): String {
            return "pomodoro/$focusName"
        }
    }
}