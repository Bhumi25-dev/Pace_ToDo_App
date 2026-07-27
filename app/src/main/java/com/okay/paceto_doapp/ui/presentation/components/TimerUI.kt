package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun TimerUI(
    canvasSize: Dp = 300.dp,
    maxIndicatorValue: Int,
    restartTrigger : Int = 0,
    isPlaying : Boolean,
    onTimerFinished : () -> Unit,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    indicatorStrokeWidth: Float = 20f,
) {
    val totalSeconds = remember(maxIndicatorValue){ maxIndicatorValue*60 }
    var secondsRemaining by remember { mutableIntStateOf(totalSeconds) }

    LaunchedEffect(key1 = restartTrigger) {
        if(restartTrigger > 0){
            secondsRemaining = totalSeconds
        }
    }

    LaunchedEffect(key1 = isPlaying) {
        while (isPlaying && secondsRemaining > 0)
        {
            delay(1000L)
            secondsRemaining--
        }

        if(secondsRemaining == 0 && isPlaying) onTimerFinished()
    }

    val liveValueAnimated by animateFloatAsState(
        targetValue = secondsRemaining.toFloat(),
        animationSpec = tween(durationMillis = 1000),
        label = "timer_progress"
    )

    val percentage = (liveValueAnimated / totalSeconds) * 100
    val sweepAngle = (3.6f * percentage)

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind{
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = indicatorStrokeWidth,
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = indicatorStrokeWidth,
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        EmbeddedElements(duration = liveValueAnimated.toInt())
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = -90f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )
}

@Composable
fun EmbeddedElements(
    duration : Int
){
    val hours = duration/3600
    val minutes = (duration%3600) / 60
    val seconds = duration % 60

    val hoursString = String.format(Locale.ROOT,"%02d",hours)
    val minutesString = String.format(Locale.ROOT,"%02d",minutes)
    val secondsString = String.format(Locale.ROOT,"%02d",seconds)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (hours > 0) {
                Text(
                    text = hoursString,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ":",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            Text(
                text = minutesString,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = ":",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            Text(
                text = secondsString,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary // Give seconds a pop of accent color
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "TIME REMAINING",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                fontSize = 12.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerUIPreview()
{
    TimerUI(
        maxIndicatorValue = 10,
        isPlaying = true,
        onTimerFinished = {}
    )
}