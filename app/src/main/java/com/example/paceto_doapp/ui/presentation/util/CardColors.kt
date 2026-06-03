package com.example.paceto_doapp.ui.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getCardContainerColor(isCompleted: Boolean, isUrgent: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.surfaceVariant
        isUrgent         -> MaterialTheme.colorScheme.errorContainer
        else             -> MaterialTheme.colorScheme.surface
    }
}

@Composable
fun getCardContentColor(isCompleted: Boolean, isUrgent: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.onSurfaceVariant
        isUrgent -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onSurface
    }
}

@Composable
fun getCardBorderColor(isCompleted: Boolean, isUrgent: Boolean): Color {
    return when {
        isUrgent         -> MaterialTheme.colorScheme.error
        isCompleted -> MaterialTheme.colorScheme.outlineVariant
        else             -> MaterialTheme.colorScheme.outlineVariant
    }
}

@Composable
fun underlineColor(isUrgent: Boolean) : Color {
    return if (isUrgent) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary
}