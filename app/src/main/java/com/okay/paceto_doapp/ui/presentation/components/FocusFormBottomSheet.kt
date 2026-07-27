package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.local.durations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusFormBottomSheet(
    focusTask: FocusTask?,
    discardRitual: () -> Unit,
    sheetState: SheetState,
    onSaveRitual: (FocusTask) -> Unit
) {

    var focusName by remember(focusTask) {mutableStateOf(focusTask?.focusName?: "")}
    var durationName by remember(focusTask) {mutableStateOf(focusTask?.durationName?: "")}
    var durationInt by remember(focusTask) {mutableIntStateOf(focusTask?.duration?: 0)}

    var expanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = discardRitual,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {

            Text(
                text = if (focusTask == null) "Create New Ritual" else "Edit Ritual",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ritual Name"
            )
            OutlinedTextField(
                value = focusName,
                onValueChange = { focusName = it },
                label = { Text("Focus Name (e.g., Workout, DSA)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ritual Name"
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = if(durationInt>0) "$durationName (${durationInt}m)" else durationName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Duration Configuration")},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    durations.forEach { duration ->
                        DropdownMenuItem(
                            text = { Text(text = "${duration.name} (${duration.durationMin}m)") },
                            onClick = {
                                durationName = duration.name
                                durationInt = duration.durationMin
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if(focusName.isNotBlank() && durationInt>0) onSaveRitual(FocusTask(focusName,durationName,durationInt))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = focusName.isNotBlank() && durationInt>0
            ) {
                Text(text = "Save Ritual")
            }
        }
    }
}