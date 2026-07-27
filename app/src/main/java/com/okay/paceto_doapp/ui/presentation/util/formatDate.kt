package com.okay.paceto_doapp.ui.presentation.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(date: LocalDateTime): String{

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter)
}