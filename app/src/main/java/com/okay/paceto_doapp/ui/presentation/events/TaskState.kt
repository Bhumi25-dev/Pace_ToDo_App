package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Category
import com.okay.paceto_doapp.data.local.Task

data class TaskState (
    val tasks : List<Task> = emptyList(),
    val selectedCategory: Category? = null,
    val markedAsComplete: Boolean = false
)