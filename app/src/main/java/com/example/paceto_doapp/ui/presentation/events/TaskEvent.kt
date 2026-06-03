package com.example.paceto_doapp.ui.presentation.events

import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Task

sealed interface TaskEvent {
    data class DeleteTask(val task: Task): TaskEvent
    data class UpdateTaskIsCompleted(val taskId: String, val isCompleted: Boolean): TaskEvent
    data class SetCategoryFilter(val category: Category): TaskEvent
}