package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Task
import java.time.LocalDate

data class CalendarUiState(
    val tasksByDate: Map<LocalDate,List<Task>> = emptyMap(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedDateTasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
)
