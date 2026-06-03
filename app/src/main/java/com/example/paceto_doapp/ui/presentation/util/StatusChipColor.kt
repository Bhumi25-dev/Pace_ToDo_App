package com.example.paceto_doapp.ui.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun statusChipContentColor(isCompleted: Boolean) : Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.onSecondaryContainer
    }
}

@Composable
fun statusChipColor(isCompleted: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.secondaryContainer
    }
}