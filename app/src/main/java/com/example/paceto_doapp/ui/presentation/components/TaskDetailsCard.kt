package com.example.paceto_doapp.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Priority
import com.example.paceto_doapp.data.local.Task
import com.example.paceto_doapp.ui.icons.reopen_window
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.daysChipColor
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.daysChipContainerColor
import com.example.paceto_doapp.ui.presentation.util.formatDate
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.getCardBorderColor
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.getCardContainerColor
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.getCardContentColor
import com.example.paceto_doapp.ui.presentation.util.getCategoryIcon
import com.example.paceto_doapp.ui.presentation.util.getIsCompletedIcon
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.iconBoxColor
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.iconTint
import com.example.paceto_doapp.ui.presentation.util.isUrgent
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.markAsCompletedBorder
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.markAsCompletedColor
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.markAsCompletedTint
import com.example.paceto_doapp.ui.presentation.util.remainingTime
import com.example.paceto_doapp.ui.presentation.util.getColorMethods.underlineColor
import com.example.paceto_doapp.ui.theme.PaceToDoAppTheme
import java.time.LocalDate
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
        TaskDetailsCard(
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
            onNavigateBack = {},
            onMarkedAsComplete = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Urgent – Light")
@Preview(showBackground = true, name = "Urgent – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewUrgent() {
    PaceToDoAppTheme {
        TaskDetailsCard(
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
            onNavigateBack = {},
            onMarkedAsComplete = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Completed – Light")
@Preview(showBackground = true, name = "Completed – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCompleted() {
    PaceToDoAppTheme {
        TaskDetailsCard(
            task = Task(
                id = UUID.randomUUID().toString(),
                title = "Create TODO App",
                description = "Done and dusted",
                startDate = LocalDateTime.now().minusDays(3),
                endDate = LocalDateTime.now().minusDays(1),
                taskCompleteDate = LocalDate.now().minusDays(2),
                category = Category.FOCUS,
                tags = emptyList(),
                priority = Priority.LOW,
                isCompleted = true,
            ),
            onNavigateBack = {},
            onMarkedAsComplete = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

// ─────────────────────────────────────────────
//  CARD
// ─────────────────────────────────────────────
@Composable
fun TaskDetailsCard(
    task: Task,
    onNavigateBack: () -> Unit,
    onMarkedAsComplete: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    val isCompleted = task.isCompleted
    val isUrgent = task.isUrgent()
    val isDark = isSystemInDarkTheme()

    // ── Corner badge (category name) ─────────────────────────────────────────
    // Always tertiaryContainer — it's a metadata label, not a state indicator,
    // so it stays consistent and doesn't fight with card-level urgency colors.
    val badgeColor = MaterialTheme.colorScheme.tertiaryContainer
    val badgeContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val cardColors = getCardContainerColor(isCompleted,isUrgent)

    // ─────────────────────────────────────────────────────────────────────────
    // The main outer container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, getCardBorderColor(isCompleted, isUrgent)),
        colors = CardDefaults.cardColors(
            containerColor = cardColors,
            contentColor = getCardContentColor(isCompleted, isUrgent)
        )
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
            ) {
                //--TOP BAR--
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 26.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    //--Title & Underline--
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(top = 4.dp),
                            thickness = 3.dp,
                            color = underlineColor(isUrgent)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    //--Days Left Chip--
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = daysChipColor(isCompleted, isUrgent)
                    ) {
                        Text(
                            text = remainingTime(task),
                            color = daysChipContainerColor(isCompleted, isUrgent),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                //--Middle Column--
                Column(
                    modifier = Modifier.padding()
                ) {
                    //--Description Box--
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Transparent.copy(alpha = 0.1f),
                        border = BorderStroke(
                            1.dp,
                            getCardBorderColor(isCompleted, isUrgent).copy(0.2f)
                        )
                    ) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    //--Clock Icon & Dates--
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange, // Ensure this exists in your drawable folder
                            contentDescription = "Clock",
                            tint = getCardContentColor(
                                isCompleted,
                                isUrgent
                            ).copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        // Formatted Dates with spacing
                        Text(
                            text = "${formatDate(task.startDate)} - ${formatDate(task.endDate)}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    //--Category--
                    FullWidthButton(
                        title = task.category.name,
                        icon = getCategoryIcon(task.category),
                        buttonTint = iconTint(isCompleted, isUrgent),
                        buttonColor = iconBoxColor(isCompleted, isUrgent),
                        borderColor = iconTint(isCompleted,isUrgent),
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    //--Mark As Complete Button
                    FullWidthButton(
                        title = if (isCompleted) "Task Finished on ${task.taskCompleteDate.toString()}" else "Mark as Complete",
                        icon = getIsCompletedIcon(isCompleted),
                        buttonTint = markAsCompletedTint(isCompleted),
                        buttonColor = markAsCompletedColor(isCompleted),
                        borderColor = markAsCompletedBorder(isCompleted),
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { if(!isCompleted) onMarkedAsComplete(task.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (!isCompleted) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SmallButton(
                                icon = Icons.Default.Edit,
                                iconTitle = "Edit",
                                onClick = { onEditClick(task.id) }
                            )
                            SmallButton(
                                icon = Icons.Default.Delete,
                                iconTitle = "Delete",
                                onClick = { onDeleteClick(task.id) }
                            )
                        }
                    } else {
                        FullWidthButton(
                            title = "Reopen Task",
                            icon = reopen_window,
                            buttonTint = markAsCompletedTint(isCompleted),
                            buttonColor = markAsCompletedColor(isCompleted),
                            borderColor = markAsCompletedTint(isCompleted),
                            modifier = Modifier.padding(),
                            onClick = { onMarkedAsComplete(task.id) }
                        )
                    }
                }
            }
            IconButton(
                onClick = { onNavigateBack() },
                modifier = Modifier.align(alignment = Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "to navigate back"
                )

            }
        }
    }

}