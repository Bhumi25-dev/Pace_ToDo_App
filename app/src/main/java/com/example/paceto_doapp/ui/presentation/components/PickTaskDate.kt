package com.example.paceto_doapp.ui.presentation.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickTaskDate(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit
) {

    if (!isOpen) return

    val datePickerState =
        rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                // Convert the raw milliseconds into your LocalDateTime
                datePickerState.selectedDateMillis?.let { millis ->
                    val pickedDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()

                    onConfirm(pickedDate) // Send it up!
                }
                onDismiss() // Close the dialog
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