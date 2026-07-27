package com.okay.paceto_doapp.ui.presentation.util

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Draws a soft glowing border behind the composable using BlurMaskFilter.
 * The glow pulses between [minAlpha] and 1f for a living, breathing effect.
 *
 * @param color        The glow color (typically your brand primary)
 * @param glowRadius   How far the glow spreads (default 12.dp)
 * @param cornerRadius Corner radius of the glow rectangle (match your card shape)
 * @param alpha        Static alpha if you don't want pulse animation (null = animated)
 */
fun Modifier.glowBorder(
    color: Color,
    glowRadius: Dp = 12.dp,
    cornerRadius: Dp = 12.dp,
    alpha: Float? = null
): Modifier = composed {
    val finalAlpha by if (alpha == null) {
        val infiniteTransition = rememberInfiniteTransition(label = "glow_pulse")
        infiniteTransition.animateFloat(
            initialValue = 0.45f,
            targetValue = 0.9f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glow_alpha_internal"
        )
    } else {
        remember(alpha) { mutableStateOf(alpha) }
    }

    val radiusPx = glowRadius.value
    val cornerRadiusPx = cornerRadius.value

    drawBehind {

        val width = size.width
        val height = size.height
        val layers = 5
        val baseAlpha = finalAlpha / layers

        for (i in 1..layers) {
            // Gradually scale out the glow radius per layer
            val spread = radiusPx * (i.toFloat() / layers)

            drawRoundRect(
                color = color.copy(alpha = baseAlpha),
                topLeft = androidx.compose.ui.geometry.Offset(-spread, -spread),
                size = androidx.compose.ui.geometry.Size(width + (spread * 2), height + (spread * 2)),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    cornerRadiusPx + spread,
                    cornerRadiusPx + spread
                )
            )
        }
    }
}

/**
 * Draws an animated shimmer sweep across the composable surface.
 * Unlike the skeleton shimmerEffect (which replaces background),
 * this overlays a translucent glowing highlight on top of any content.
 * Perfect for loaded cards, badges, and icon boxes.
 *
 * @param highlightColor The sweep highlight (typically white or brand orange at low alpha)
 * @param durationMs     Speed of one full sweep cycle
 */
fun Modifier.shimmerSweep(
    highlightColor: Color = Color.White.copy(alpha = 0.08f),
    durationMs: Int = 2200
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer_sweep")
    val progress by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep_progress"
    )

    drawWithContent {
        drawContent()
        val sweepWidth = size.width * 0.45f
        val startX = progress * size.width
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    highlightColor,
                    Color.Transparent
                ),
                start = Offset(startX, 0f),
                end = Offset(startX + sweepWidth, size.height)
            )
        )
    }
}
