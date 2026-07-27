package com.okay.paceto_doapp.ui.presentation.util

import com.okay.paceto_doapp.data.local.FocusRecords
import java.time.LocalDate

fun getStreakPercentage(records: List<FocusRecords>): Int{
    if(records.isEmpty()) return 0;

    val today = LocalDate.now()
    val startDate = today.minusDays(60)

    val completedDays = records.count{ record ->
        !record.date.isBefore(startDate) && !record.date.isAfter(today)
    }

    return ((completedDays/60f)*100).toInt()
}