package com.example.paceto_doapp.ui.presentation.events

import com.example.paceto_doapp.data.local.Task

data class HomeScreenState(
    val userName: String = "User",
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasksList: List<Task> = emptyList()
){
    val pendingTasks: Int
        get() = totalTasks - completedTasks
}
