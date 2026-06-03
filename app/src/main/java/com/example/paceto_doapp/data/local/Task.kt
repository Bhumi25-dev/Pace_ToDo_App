package com.example.paceto_doapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey
    val id : String = UUID.randomUUID().toString(),
    val title : String,
    val description: String,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime,
    val taskCompleteDate: LocalDate? = null,
    val category: Category,
    val tags : List<String> = emptyList(),
    val priority: Priority,
    val isCompleted : Boolean = false
)