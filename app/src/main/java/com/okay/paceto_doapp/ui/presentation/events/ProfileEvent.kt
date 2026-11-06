package com.okay.paceto_doapp.ui.presentation.events

sealed interface ProfileEvent {

    data class NameChanged(val name: String): ProfileEvent
    data class AgeChanged(val age: Int): ProfileEvent
    data class ProfessionChanged(val profession: String): ProfileEvent
    data class EmailChanged(val email: String): ProfileEvent
    data class SaveProfile(val onSuccessfulSave: () -> Unit): ProfileEvent
}