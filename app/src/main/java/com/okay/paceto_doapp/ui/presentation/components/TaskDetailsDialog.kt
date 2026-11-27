package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.okay.paceto_doapp.data.local.Task

/**
 * Shared modal dialog wrapping [TaskDetailsCard] for inspecting, completing, editing, or deleting a task.
 */
@Composable
fun TaskDetailsDialog(
    task: Task,
    onDismiss: () -> Unit,
    onToggleCompleted: (Task) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        TaskDetailsCard(
            task = task,
            onNavigateBack = onDismiss,
            onMarkedAsComplete = { onToggleCompleted(task) },
            onEditClick = {
                onDismiss()
                onEditClick(task.id)
            },
            onDeleteClick = {
                onDeleteClick(task)
                onDismiss()
            }
        )
    }
}
