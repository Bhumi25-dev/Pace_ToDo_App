@file:Suppress("DEPRECATION")

package com.example.paceto_doapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ─── Light — warm, earthy, cozy ──────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary                 = WarmTerracotta,
    onPrimary               = WarmCream,
    primaryContainer        = WarmTerracottaLight,
    onPrimaryContainer      = WarmOnBackground,

    secondary               = WarmBrown,
    onSecondary             = WarmCream,
    secondaryContainer      = DaysLeftChipBg,
    onSecondaryContainer    = WarmBrown,

    tertiary                = WarmSand,
    onTertiary              = WarmOnBackground,
    tertiaryContainer       = WarmSurfaceVariant,
    onTertiaryContainer     = WarmOnSurface,

    background              = WarmCream,
    onBackground            = WarmOnBackground,

    surface                 = WarmSurface,
    onSurface               = WarmOnBackground,
    surfaceVariant          = WarmSurfaceVariant,
    onSurfaceVariant        = WarmOnSurface,

    outline                 = WarmOutline,
    outlineVariant          = WarmSurfaceVariant,

    error                   = TaskUrgentText,
    onError                 = WarmCream,
    errorContainer          = TaskUrgentChip,
    onErrorContainer        = TaskUrgentText,
)

// ─── Dark — intense, ember-lit, high contrast ─────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary                 = EmberOrange,
    onPrimary               = CharcoalBackground,
    primaryContainer        = EmberDeep,
    onPrimaryContainer      = WarmWhite,

    secondary               = EmberLight,
    onSecondary             = CharcoalBackground,
    secondaryContainer      = CharcoalSurfaceVar,
    onSecondaryContainer    = EmberLight,

    tertiary                = BurntTertiary,
    onTertiary              = CharcoalBackground,
    tertiaryContainer       = CharcoalSurface,
    onTertiaryContainer     = WarmWhiteSecondary,

    background              = CharcoalBackground,
    onBackground            = WarmWhite,

    surface                 = CharcoalSurface,
    onSurface               = WarmWhite,
    surfaceVariant          = CharcoalSurfaceVar,
    onSurfaceVariant        = WarmWhiteSecondary,

    outline                 = DarkOutline,
    outlineVariant          = CharcoalSurfaceVar,

    error                   = TaskUrgentTextDark,
    onError                 = CharcoalBackground,
    errorContainer          = TaskUrgentSurfaceDark,
    onErrorContainer        = TaskUrgentTextDark,
)

@Composable
fun PaceToDoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Dynamic color intentionally disabled — our warm/intense palette IS the brand.
    // Enabling dynamic color would replace it with the wallpaper palette on Android 12+.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
