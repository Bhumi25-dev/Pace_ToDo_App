package com.example.paceto_doapp.ui.presentation.events

import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Task

data class TaskState (
    val tasks : List<Task> = emptyList(),
    val selectedCategory: Category? = null
)