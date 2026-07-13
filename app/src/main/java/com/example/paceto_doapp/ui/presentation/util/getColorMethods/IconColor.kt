package com.example.paceto_doapp.ui.presentation.util.getColorMethods

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun iconBoxColor(isCompleted: Boolean, isUrgent: Boolean) : Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.tertiaryContainer
        isUrgent         -> MaterialTheme.colorScheme.error
        else             -> MaterialTheme.colorScheme.secondaryContainer
    }

}

@Composable
fun iconTint(isCompleted: Boolean, isUrgent: Boolean) : Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.onTertiaryContainer
        isUrgent         -> MaterialTheme.colorScheme.onError
        else             -> MaterialTheme.colorScheme.onSecondaryContainer
    }
}

//content color
@Composable
fun markAsCompletedColor(isCompleted: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.onSurfaceVariant          // Soft accent checkmark icon
        else        -> MaterialTheme.colorScheme.onPrimaryContainer        // High contrast dark foreground element
    }
}

//container color
@Composable
fun markAsCompletedTint(isCompleted: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.surfaceContainerHighest   // Neutralized empty container
        else        -> MaterialTheme.colorScheme.primaryContainer          // Pops out in radiant Cyber Orange
    }
}

@Composable
fun markAsCompletedBorder(isCompleted: Boolean): Color {
    return when {
        isCompleted -> MaterialTheme.colorScheme.outlineVariant
        else        -> MaterialTheme.colorScheme.primaryContainer          // High-energy focus outline element
    }
}