package com.example.paceto_doapp.ui.presentation.navigation

import android.health.connect.datatypes.ExerciseRoute
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val route: String,
    val title: String,
    val icon: ImageVector
)