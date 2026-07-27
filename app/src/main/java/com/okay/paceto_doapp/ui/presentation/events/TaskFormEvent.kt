package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Category
import com.okay.paceto_doapp.data.local.Priority
import java.time.LocalDateTime

sealed interface TaskFormEvent {
    data class TitleChanged(val title: String): TaskFormEvent
    data class DescriptionChanged(val des: String): TaskFormEvent

    data class StartDateChanged(val date: LocalDateTime) : TaskFormEvent
    data class EndDateChanged(val date: LocalDateTime) : TaskFormEvent

    data class CategorySelected(val category: Category): TaskFormEvent
    data class PrioritySelected(val priority: Priority): TaskFormEvent

    data class SaveTask(val onSuccessfulSave: () -> Unit): TaskFormEvent
    object CancelTask: TaskFormEvent
}