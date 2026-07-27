package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Category
import com.okay.paceto_doapp.data.local.Priority
import java.time.LocalDateTime

data class TaskFormState(
    val id: String? = null,
    val topBarTitle: String = "Create Task",
    val title: String = "",
    val description: String = "",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val category: Category = Category.FOCUS,
    val priority: Priority = Priority.LOW,
    val tags: List<String> = emptyList(),
    val titleError: Boolean = false,
    val titleErrorMessage: String = "",
    val isSaving: Boolean = false
)
