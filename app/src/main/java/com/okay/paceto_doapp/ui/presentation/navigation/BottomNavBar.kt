package com.okay.paceto_doapp.ui.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.okay.paceto_doapp.ui.presentation.util.glowBorder

@Composable
fun BottomNavBar(navController: NavController) {

    val items = listOf(
        BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home),
        BottomNavItem(Screen.FocusList.route, "Ritual", Icons.AutoMirrored.Filled.List)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val primaryColor = MaterialTheme.colorScheme.primary

    // Single parent animation loop for the bar selection glow
    val glowPulse = rememberInfiniteTransition(label = "icon_glow_pulse")
    val glowAlpha by glowPulse.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_glow_alpha"
    )

    // Glass surface colors
    val glassBase = MaterialTheme.colorScheme.surfaceContainerHigh
    val glassTop  = MaterialTheme.colorScheme.surfaceContainerHighest

    Box(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        // ── Floating capsule ─────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.4f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                )
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            glassTop.copy(alpha = 0.97f),
                            glassBase.copy(alpha = 0.99f)
                        )
                    )
                )
                .drawBehind {
                    // Top hairline reflection rim
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.12f),
                                Color.White.copy(alpha = 0.18f),
                                Color.White.copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                    // Bottom shadow hairline
                    drawLine(
                        color = Color.Black.copy(alpha = 0.3f),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 0.5.dp.toPx()
                    )
                }
        ) {
            // ── Nav Items Row ─────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    // Smooth icon scale spring on tap
                    val iconScale by animateFloatAsState(
                        targetValue = if (isSelected) 1.15f else 1f,
                        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
                        label = "icon_scale_${item.route}"
                    )

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        color = Color.Transparent,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(route = item.route) {
                                    val startRoute =
                                        navController.graph.findStartDestination().route
                                            ?: Screen.Home.route
                                    popUpTo(startRoute)
                                    launchSingleTop = true
                                }
                            } else {
                                navController.popBackStack(Screen.Home.route, inclusive = false)
                            }
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.height(64.dp)
                        ) {

                            // ── Icon area ─────────────────────────────────────
                            Box(
                                modifier = Modifier
                                    .scale(iconScale)
                                    .animateContentSize(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                    .then(
                                        if (isSelected) {
                                            Modifier
                                                .clip(RoundedCornerShape(40))
                                                .background(primaryColor.copy(alpha = 0.15f))
                                                // Passes glowAlpha down explicitly. GlowModifier skips internal animations now!
                                                .glowBorder(
                                                    color = primaryColor,
                                                    glowRadius = 12.dp,
                                                    cornerRadius = 40.dp,
                                                    alpha = glowAlpha
                                                )
                                        } else {
                                            Modifier
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(horizontal = if (isSelected) 12.dp else 8.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = if (isSelected) primaryColor
                                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.size(22.dp)
                                    )

                                    // Smoothly slides and fades the screen title label text out from the icon
                                    AnimatedVisibility(
                                        visible = isSelected,
                                        enter = fadeIn(animationSpec = tween(200)) +
                                                slideInHorizontally(animationSpec = tween(200)) { -10 },
                                        exit = fadeOut(animationSpec = tween(150)) +
                                                slideOutHorizontally(animationSpec = tween(150)) { -10 }
                                    ) {
                                        Row {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = item.title,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = primaryColor,
                                                modifier = Modifier.padding(top = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}