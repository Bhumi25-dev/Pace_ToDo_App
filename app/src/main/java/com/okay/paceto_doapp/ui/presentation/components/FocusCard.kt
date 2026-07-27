package com.okay.paceto_doapp.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.local.FocusTaskWithRecords
import com.okay.paceto_doapp.ui.icons.stopWatch
import com.okay.paceto_doapp.ui.presentation.util.getColorMethods.getCardBorderColor
import com.okay.paceto_doapp.ui.presentation.util.getStreakPercentage
import com.okay.paceto_doapp.ui.presentation.util.glowBorder
import com.okay.paceto_doapp.ui.theme.PaceToDoAppTheme

@Preview(showBackground = true, name = "Pending – Light")
@Preview(showBackground = true, name = "Pending – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FocusCardPreview(){
    PaceToDoAppTheme {
        FocusCard(
            item = FocusTaskWithRecords(
                task = FocusTask(
                    focusName = "Study",
                    durationName = "Medium",
                    duration = 50
                ),
                records = emptyList()
            ),
            glowAlpha = 0.65f,
            onEditClick = {},
            onDeleteClick = {},
            onCardClick = {},
        )
    }
}

@Composable
fun FocusCard(
    item : FocusTaskWithRecords,
    // Hoisted from FocusListScreen — all cards share a single transition clock
    glowAlpha: Float,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onCardClick: (FocusTask) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    // Static gradient replacing shimmerSweep — zero animation cost
    val premiumCardBackground = Brush.verticalGradient(
        colors = listOf(
            surfaceColor,
            primaryColor.copy(alpha = 0.03f)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable{ onCardClick(item.task) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent, // Transparent lets premiumCardBackground render cleanly
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, getCardBorderColor(isCompleted = false, isUrgent = false))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(premiumCardBackground)
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.task.focusName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                //EditButton
                IconButton(
                    onClick = { onEditClick(item.task.focusName) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Focus Task",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                //DeleteButton
                IconButton(
                    onClick = { onDeleteClick(item.task.focusName) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Focus Task",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timer badge with glowing orange border
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    // Uses screen-hoisted glowAlpha — no per-card transition
                    .glowBorder(
                        color = primaryColor,
                        glowRadius = 8.dp,
                        cornerRadius = 20.dp,
                        alpha = glowAlpha
                    )
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp)
                    .size(height = 22.dp, width = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = stopWatch,
                        contentDescription = "Timer Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.task.duration}m",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "History",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${getStreakPercentage(item.records)}%",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HeatMapGrid(records = item.records)
        }
    }
}