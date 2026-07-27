package com.okay.paceto_doapp.ui.presentation.util

import com.okay.paceto_doapp.data.local.Task
import java.time.LocalDateTime
import kotlin.ranges.contains

fun Task.isUrgent(): Boolean {
    if (isCompleted) return false
    val hoursLeft = java.time.Duration.between(LocalDateTime.now(), endDate).toHours()
    return hoursLeft in 0..24
}