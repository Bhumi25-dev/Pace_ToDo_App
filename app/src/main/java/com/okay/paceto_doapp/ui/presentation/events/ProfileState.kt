package com.okay.paceto_doapp.ui.presentation.events

data class ProfileState(
    val name: String = "Okay",
    val age: Int = 21,
    val profession: String = "Android Developer",
    val email: String = "okay@example.com",
    val isSaving: Boolean = false
)