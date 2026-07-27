package com.okay.paceto_doapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FocusTask(
    @PrimaryKey
    val focusName: String,
    val durationName: String,
    val duration: Int,
)
