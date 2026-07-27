package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.okay.paceto_doapp.data.local.FocusRecords
import java.time.LocalDate

@Composable
fun HeatMapGrid(
    records: List<FocusRecords>,
    daysToShow: Int = 60
) {
    val completedDays = remember(records) {
        records.map { it.date }.toSet()
    }

    // Generate date sequence chunked cleanly into groups of 7 rows
    val rowsData = remember(daysToShow) {
        val today = LocalDate.now()
        val totalDaysList = (daysToShow downTo 0).map { daysAgo ->
            today.minusDays(daysAgo.toLong())
        }

        // Group individual days into 7 buckets (rows) based on their day-of-the-week index
        val gridRows = List(7) { mutableListOf<LocalDate>() }
        totalDaysList.forEachIndexed { index, date ->
            val rowIndex = index % 7
            gridRows[rowIndex].add(date)
        }
        gridRows
    }

    // Standard static layout — extremely lightweight and zero lazy measurement penalty
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rowsData.forEach { rowDates ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowDates.forEach { date ->
                    val isCompleted = completedDays.contains(date)
                    val squareColor = if (isCompleted) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }

                    Box(
                        modifier = Modifier
                            .size(22.dp) // Adjusted slightly to allow 60 days to fit nicely across mobile screen widths
                            .clip(RoundedCornerShape(4.dp))
                            .background(squareColor)
                    )
                }
            }
        }
    }
}