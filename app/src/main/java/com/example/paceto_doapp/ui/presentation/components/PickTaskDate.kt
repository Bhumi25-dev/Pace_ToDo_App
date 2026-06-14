package com.example.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

enum class DateTimePick {
    DATE,
    TIME
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickTaskDate(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit
) {
    var currentStep by remember { mutableStateOf(DateTimePick.DATE) }
    var storedDate by remember { mutableStateOf<LocalDate?>(null) }

    if (!isOpen) {
        currentStep = DateTimePick.DATE
        return
    }

    when (currentStep) {
        DateTimePick.DATE -> {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = { onDismiss() },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            // Extract only the LocalDate
                            val pickedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()

                            storedDate = pickedDate
                            currentStep = DateTimePick.TIME
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onDismiss() }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        DateTimePick.TIME -> {
            val currentTime = LocalTime.now()
            val timePickerState = rememberTimePickerState(
                initialHour = currentTime.hour,
                initialMinute = currentTime.minute,
                is24Hour = false
            )

            AlertDialog(
                onDismissRequest = { onDismiss() },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)

                        storedDate?.let { date ->
                            val finalDateTime = LocalDateTime.of(date, selectedTime)
                            onConfirm(finalDateTime)
                        }

                        onDismiss()
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onDismiss() }) { Text("Cancel") }
                },
                text = {
                    Column {
                        TimePicker(state = timePickerState)
                    }
                }
            )
        }
    }
}