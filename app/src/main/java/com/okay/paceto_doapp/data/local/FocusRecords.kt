package com.okay.paceto_doapp.data.local

import androidx.room.Entity
import java.time.LocalDate

@Entity(primaryKeys = ["date","focusName"])
data class FocusRecords(
    val date: LocalDate,
    val focusName: String,
    val duration: Int,
    val sessionCount : Int
)
