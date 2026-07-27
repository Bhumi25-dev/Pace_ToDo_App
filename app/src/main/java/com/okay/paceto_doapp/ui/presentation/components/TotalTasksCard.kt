package com.okay.paceto_doapp.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okay.paceto_doapp.ui.presentation.util.glowBorder
import com.okay.paceto_doapp.ui.theme.PaceToDoAppTheme
import com.okay.test.all_tasks

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewPending() {
    PaceToDoAppTheme {
        TotalTasksCard(
            totalTasks = 24,
            completedTasks = 16,
            pendingTasks = 8
        )
    }
}

@Composable
fun TotalTasksCard(
    totalTasks: Int,
    completedTasks: Int,
    pendingTasks: Int,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surfaceContainerHigh

    // ── 1. CONSOLIDATED TRANSITION CLOCK ─────────────────────────────────
    val sharedTransition = rememberInfiniteTransition(label = "total_tasks_card_pulse")

    val animatedGlowAlpha by sharedTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shared_glow_alpha"
    )

    val animatedIconScale by sharedTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shared_icon_scale"
    )

    // ── 2. BRAND-TINTED STATIC REPLACEMENT FOR SHIMMER ────────────────────
    val premiumCardBackground = Brush.verticalGradient(
        colors = listOf(
            surfaceColor,
            primaryColor.copy(alpha = 0.04f) // Brand tint pooling cleanly at bottom
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .glowBorder(
                color = primaryColor,
                glowRadius = 10.dp,
                cornerRadius = 12.dp,
                alpha = animatedGlowAlpha
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent, // Transparent allows premiumCardBackground to shine through
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        // Removed .shimmerSweep() modifier to prevent unnecessary per-frame layout recalculations
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(premiumCardBackground)
                .padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Top Section: Icon & Total Tasks ---
            Surface(
                modifier = Modifier
                    .size(64.dp)
                    .glowBorder(
                        color = primaryColor,
                        glowRadius = 14.dp,
                        cornerRadius = 16.dp,
                        alpha = animatedGlowAlpha * 0.7f
                    ),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = all_tasks,
                        contentDescription = "Total Tasks Icon",
                        tint = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Tasks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$totalTasks tasks",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Bottom Section: Gradient Progress Bars ---
            TaskProgressRow(
                label = "Completed",
                count = completedTasks,
                total = totalTasks
            )

            Spacer(modifier = Modifier.height(24.dp))

            TaskProgressRow(
                label = "Pending",
                count = pendingTasks,
                total = totalTasks
            )
        }
    }
}

@Composable
fun TaskProgressRow(
    label: String,
    count: Int,
    total: Int
) {
    val progress = if (total > 0) count.toFloat() / total.toFloat() else 0f
    val primaryColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceContainerHighest

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(trackColor)
        ) {
            if (progress > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.6f),
                                    primaryColor,
                                    primaryColor.copy(alpha = 0.9f),
                                )
                            )
                        )
                        .glowBorder(
                            color = primaryColor,
                            glowRadius = 6.dp,
                            cornerRadius = 50.dp,
                            alpha = 0.7f // Keeping static alpha for the indicator cap
                        )
                )
            }
        }
    }
}