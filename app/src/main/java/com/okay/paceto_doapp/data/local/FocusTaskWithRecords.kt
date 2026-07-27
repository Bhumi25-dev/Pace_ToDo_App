package com.okay.paceto_doapp.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class FocusTaskWithRecords (
    @Embedded val task: FocusTask,
    @Relation(
        parentColumn = "focusName",
        entityColumn = "focusName" //foreign key in FocusRecords
    )
    val records: List<FocusRecords>
)