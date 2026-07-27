package com.okay.paceto_doapp.ui.presentation.events

import com.okay.paceto_doapp.data.local.Duration

sealed class FocusListEvent {
    data class UpdateFocusTask(val focusName: String, val durationName: String, val duration: Int): FocusListEvent()
    data class DeleteFocusTask(val focusName: String): FocusListEvent()
    data class SetDurationFilter(val duration: Duration?): FocusListEvent()
}