package com.okay.paceto_doapp.ui.presentation.util

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

/**
 * Classic skeleton shimmer — used on placeholder loading bones.
 * Sweeps a light gradient across a dark surface container background.
 */
fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "cyber_shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -300f,
        targetValue = 1300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_move"
    )

    // Pulling high-contrast structural container boundaries from your design.md
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceContainer,
        MaterialTheme.colorScheme.surfaceContainerHighest,
        MaterialTheme.colorScheme.surfaceContainer
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 250f, translateAnim + 250f)
    )

    this.background(brush)
}
