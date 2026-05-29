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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Priority
import com.example.paceto_doapp.data.local.Task
import com.example.paceto_doapp.ui.presentation.util.formatDate
import com.example.paceto_doapp.ui.presentation.util.getCategoryIcon
import com.example.paceto_doapp.ui.presentation.util.remainingTime
import com.example.paceto_doapp.ui.theme.PaceToDoAppTheme
import java.time.LocalDateTime
import java.util.UUID

@Preview(showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GreetingPreview() {
    PaceToDoAppTheme {
        val task = Task(
            id = UUID.randomUUID().toString(),
            title = "Create TODO App",
            description = "Creating a superior TODO app to practice android dev",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.of(2026, 5, 13, 23, 15),
            category = Category.HEALTH,
            tags = emptyList(),
            priority = Priority.HIGH,
            isCompleted = true,
        )
        TaskScreenCard(task)
    }
}

@Composable
fun TaskScreenCard(task: Task) {

    // The main outer container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {}
    ) {
        // 1. THE OVERLAP BOX: This allows the badge to sit in the absolute top corner
        Box(modifier = Modifier.fillMaxWidth()) {

            // 2. THE MAIN CONTENT
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
                        )
                        // The Wireframe Underline
                        HorizontalDivider(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(top = 4.dp),
                            thickness = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Clock Icon and dates
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.DateRange, // Ensure this exists in your drawable folder
                                contentDescription = "Clock",
                                tint = MaterialTheme.colorScheme.secondary,
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
                        color = MaterialTheme.colorScheme.secondaryContainer,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = getCategoryIcon(task.category),
                                contentDescription = "Category Icon",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
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
                        color = if (task.isCompleted) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                    ) {
                        Text(
                            text = if (task.isCompleted) "Completed" else "Pending",
                            color = if (task.isCompleted) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    // "Days Left" Label
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = remainingTime(task),
                            color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
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
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = task.category.name,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}