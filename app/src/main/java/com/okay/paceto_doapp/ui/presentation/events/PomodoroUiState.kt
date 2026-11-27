package com.okay.paceto_doapp.ui.presentation.events

data class PomodoroUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isSaving: Boolean = false,
    val isSavedSuccess: Boolean = false
)
