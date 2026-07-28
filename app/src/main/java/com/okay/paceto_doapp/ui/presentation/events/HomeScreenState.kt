package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Task
import kotlinx.coroutines.flow.StateFlow

data class HomeScreenState(
    val userName: String? = null,
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasksList: List<Task> = emptyList()
){
    val pendingTasks: Int
        get() = totalTasks - completedTasks
}
