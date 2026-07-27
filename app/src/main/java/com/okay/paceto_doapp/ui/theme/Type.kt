package com.okay.paceto_doapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    // App title / screen heading
    displaySmall = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.Bold,
        fontSize     = 32.sp,
        lineHeight   = 40.sp,
        letterSpacing = (-0.5).sp,
    ),

    // Section / list headers
    headlineMedium = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 22.sp,
        lineHeight   = 28.sp,
        letterSpacing = (-0.2).sp,
    ),

    // Card title — task name
    titleLarge = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 18.sp,
        lineHeight   = 24.sp,
        letterSpacing = 0.sp,
    ),

    // Sub-section label, card group header
    titleMedium = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.Medium,
        fontSize     = 15.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Task description / body text
    bodyLarge = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.Normal,
        fontSize     = 16.sp,
        lineHeight   = 24.sp,
        letterSpacing = 0.3.sp,
    ),

    // Secondary info on cards, timestamps
    bodyMedium = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.Normal,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.2.sp,
    ),

    // Chips (days left, status badges)
    labelSmall = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.Medium,
        fontSize     = 11.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    // Button labels
    labelLarge = TextStyle(
        fontFamily   = FontFamily.Default,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.1.sp,
    ),
)
