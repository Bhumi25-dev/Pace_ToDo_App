package com.example.paceto_doapp.ui.presentation.util.getColorMethods

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun daysChipColor(isCompleted: Boolean, isUrgent: Boolean) : Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.surfaceVariant
        isUrgent -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondaryContainer
    }
}


@Composable
fun daysChipContainerColor(isCompleted: Boolean,isUrgent: Boolean) : Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.onSurfaceVariant
        isUrgent -> MaterialTheme.colorScheme.onError
        else -> MaterialTheme.colorScheme.onSecondaryContainer
    }
}