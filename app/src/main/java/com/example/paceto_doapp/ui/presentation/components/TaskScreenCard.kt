package com.example.paceto_doapp.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Priority
import com.example.paceto_doapp.data.local.Task
import com.example.paceto_doapp.ui.presentation.util.daysChipColor
import com.example.paceto_doapp.ui.presentation.util.daysChipContainerColor
import com.example.paceto_doapp.ui.presentation.util.formatDate
import com.example.paceto_doapp.ui.presentation.util.getCardBorderColor
import com.example.paceto_doapp.ui.presentation.util.getCardContainerColor
import com.example.paceto_doapp.ui.presentation.util.getCardContentColor
import com.example.paceto_doapp.ui.presentation.util.getCategoryIcon
import com.example.paceto_doapp.ui.presentation.util.iconBoxColor
import com.example.paceto_doapp.ui.presentation.util.iconTint
import com.example.paceto_doapp.ui.presentation.util.isUrgent
import com.example.paceto_doapp.ui.presentation.util.remainingTime
import com.example.paceto_doapp.ui.presentation.util.statusChipColor
import com.example.paceto_doapp.ui.presentation.util.statusChipContentColor
import com.example.paceto_doapp.ui.presentation.util.underlineColor
import com.example.paceto_doapp.ui.theme.PaceToDoAppTheme
import java.time.LocalDateTime
import java.util.UUID

// ─────────────────────────────────────────────
//  PREVIEWS — one per card state
// ─────────────────────────────────────────────

@Preview(showBackground = true, name = "Pending – Light")
@Preview(showBackground = true, name = "Pending – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewPending() {
    PaceToDoAppTheme {
        TaskScreenCard(
            task = Task(
                id = UUID.randomUUID().toString(),
                title = "Design System Audit",
                description = "Review all colour tokens",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusDays(5),
                category = Category.HEALTH,
                tags = emptyList(),
                priority = Priority.MEDIUM,
                isCompleted = false,
            ),
            onCardClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Urgent – Light")
@Preview(showBackground = true, name = "Urgent – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewUrgent() {
    PaceToDoAppTheme {
        TaskScreenCard(
            task = Task(
                id = UUID.randomUUID().toString(),
                title = "Submit Report",
                description = "Due in a few hours!",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(3),
                category = Category.ADMIN,
                tags = emptyList(),
                priority = Priority.HIGH,
                isCompleted = false,
            ),
            onCardClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Completed – Light")
@Preview(showBackground = true, name = "Completed – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCompleted() {
    PaceToDoAppTheme {
        TaskScreenCard(
            task = Task(
                id = UUID.randomUUID().toString(),
                title = "Create TODO App",
                description = "Done and dusted",
                startDate = LocalDateTime.now().minusDays(3),
                endDate = LocalDateTime.now().minusDays(1),
                category = Category.FOCUS,
                tags = emptyList(),
                priority = Priority.LOW,
                isCompleted = true,
            ),
            onCardClick = {}
        )
    }
}

// ─────────────────────────────────────────────
//  CARD
// ─────────────────────────────────────────────
@Composable
fun TaskScreenCard(
    task: Task,
    onCardClick: (taskId: String) -> Unit
) {
    val isUrgent = task.isUrgent()

    // ── Corner badge (category name) ─────────────────────────────────────────
    // Always tertiaryContainer — it's a metadata label, not a state indicator,
    // so it stays consistent and doesn't fight with card-level urgency colors.
    val badgeColor        = MaterialTheme.colorScheme.tertiaryContainer
    val badgeContentColor = MaterialTheme.colorScheme.onTertiaryContainer

    // ─────────────────────────────────────────────────────────────────────────
    // The main outer container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, getCardBorderColor(task.isCompleted, isUrgent)),
        colors = CardDefaults.cardColors(
            containerColor = getCardContainerColor(task.isCompleted, isUrgent),
            contentColor = getCardContentColor(task.isCompleted, isUrgent)
        ),
        onClick = {onCardClick(task.id)}
    ) {
        // 1. THE OVERLAP BOX: This allows the badge to sit in the absolute top corner
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 2. THE MAIN CONTENT
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                // --- TOP SECTION (Title, Dates, and Icon) ---
                // ADDED: fillMaxWidth and SpaceBetween to push the icon to the side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // LEFT SIDE: Title, Underline, and Dates
                    Column (modifier = Modifier.weight(1f)){
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                        // The Wireframe Underline
                        HorizontalDivider(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(top = 4.dp),
                            thickness = 3.dp,
                            color = underlineColor(isUrgent)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Clock Icon and dates
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange, // Ensure this exists in your drawable folder
                                contentDescription = "Clock",
                                tint = getCardContentColor(task.isCompleted,isUrgent).copy(alpha = 0.7f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            // Formatted Dates with spacing
                            Text(
                                text = "${formatDate(task.startDate)} - ${formatDate(task.endDate)}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    // RIGHT SIDE: Category Icon Box
                    Surface(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = iconTint(task.isCompleted,isUrgent),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = getCategoryIcon(task.category),
                                contentDescription = "Category Icon",
                                tint = iconBoxColor(task.isCompleted,isUrgent),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                // --- BOTTOM ROW ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // "Pending/Completed" Label
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = statusChipColor(task.isCompleted),
                        border = BorderStroke(1.dp,statusChipContentColor(task.isCompleted))
                    ) {
                        Text(
                            text = if (task.isCompleted) "Completed" else "Pending",
                            color = statusChipContentColor(task.isCompleted),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    // "Days Left" Label
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = daysChipColor(task.isCompleted,isUrgent)
                        ) {
                        Text(
                            text = remainingTime(task),
                            color = daysChipContainerColor(task.isCompleted,isUrgent),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
            // 3. THE CORNER BADGE
            Surface(
                modifier = Modifier.align(Alignment.TopEnd),
                shape = RoundedCornerShape(bottomStart = 12.dp, topEnd = 12.dp),
                color = badgeColor,
                border = BorderStroke(1.dp,badgeContentColor.copy(0.3f))
            ) {
                Text(
                    text = task.category.name,
                    color = badgeContentColor,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}