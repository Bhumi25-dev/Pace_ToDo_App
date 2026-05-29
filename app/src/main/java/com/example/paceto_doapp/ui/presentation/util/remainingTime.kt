package com.example.paceto_doapp.ui.presentation.util

import com.example.paceto_doapp.data.local.Task
import java.time.Duration
import java.time.LocalDateTime
import kotlin.ranges.contains

fun remainingTime(task: Task): String {

    if(task.isCompleted) return "Done"
    val now = LocalDateTime.now()
    val duration = Duration.between(now,task.endDate)

    val days = duration.toDays()
    val totalHours = duration.toHours()
    val totalMinutes = duration.toMinutes()

    return when {
        // If it's more than a day away, just show days
        days > 1 -> "$days days left"
        days == 1L -> "1 day left"

        // If it's due today (less than 24 hours), show hours
        totalHours > 1 -> "$totalHours hours left"
        totalHours == 1L -> "1 hour left"

        // If it's incredibly close (less than an hour), show minutes
        totalMinutes in 1..59 -> "$totalMinutes mins left"
        totalMinutes == 0L -> "Due right now!"

        // If the duration is negative, it's in the past
        else -> {
            val overdueDays = -days
            val overdueHours = -totalHours
            if (overdueDays > 0) "Overdue ($overdueDays days)"
            else "Overdue ($overdueHours hours)"
        }
    }

}