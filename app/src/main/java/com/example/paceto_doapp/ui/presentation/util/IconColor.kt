package com.example.paceto_doapp.ui.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.paceto_doapp.ui.theme.CharcoalBackground
import com.example.paceto_doapp.ui.theme.CharcoalSurfaceVar
import com.example.paceto_doapp.ui.theme.DarkOutline
import com.example.paceto_doapp.ui.theme.EmberOrange
import com.example.paceto_doapp.ui.theme.WarmCream
import com.example.paceto_doapp.ui.theme.WarmOnBackground
import com.example.paceto_doapp.ui.theme.WarmOutline
import com.example.paceto_doapp.ui.theme.WarmSurfaceVariant
import com.example.paceto_doapp.ui.theme.WarmWhiteSecondary

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
fun markAsCompletedColor(isDark: Boolean, isCompleted: Boolean): Color {
    return when {
        isCompleted -> if (isDark) WarmWhiteSecondary else WarmOutline
        else -> if (isDark) CharcoalBackground else WarmCream
    }
}

//container color
@Composable
fun markAsCompletedTint(isDark: Boolean, isCompleted: Boolean): Color {
    return when {
        isCompleted -> if (isDark) CharcoalSurfaceVar else WarmSurfaceVariant
        else -> if (isDark) EmberOrange else WarmOnBackground
    }
}

@Composable
fun markAsCompletedBorder(isDark: Boolean, isCompleted: Boolean): Color {
    return when {
        isCompleted -> if (isDark) DarkOutline else WarmOutline
        else -> if (isDark) EmberOrange else WarmOnBackground
    }
}