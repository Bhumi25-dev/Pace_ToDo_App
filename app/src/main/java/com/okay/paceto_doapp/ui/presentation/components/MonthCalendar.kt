package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.core.daysOfWeek
import com.okay.paceto_doapp.ui.presentation.events.CalendarUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale



@Composable
fun MonthCalendar(
    calendarState: CalendarState,
    uiState: CalendarUiState,
    onDateClick: (LocalDate) -> Unit
){

    val days = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }

    HorizontalCalendar(
        state = calendarState,
        dayContent = {day->
            DayCell(
                day = day,
                isSelected = day.date == uiState.selectedDate,
                hasTasks = !uiState.tasksByDate[day.date].isNullOrEmpty(),
                onDateClick = onDateClick
            )
        },
        monthHeader = { month ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    // A simple way to format it. You can capitalize it properly later!
                    text = "${month.yearMonth.month.name} ${month.yearMonth.year}",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                DaysOfWeekTitle(daysOfWeek = days)
            }
        }
    )
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}