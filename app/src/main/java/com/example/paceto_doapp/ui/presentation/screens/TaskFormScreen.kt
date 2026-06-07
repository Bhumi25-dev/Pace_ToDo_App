package com.example.paceto_doapp.ui.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.ui.presentation.components.CategorizationDropdown
import com.example.paceto_doapp.ui.presentation.components.DatePickerField
import com.example.paceto_doapp.ui.presentation.components.PickTaskDate
import com.example.paceto_doapp.ui.presentation.components.TaskTextField
import com.example.paceto_doapp.ui.presentation.events.TaskFormEvent
import com.example.paceto_doapp.ui.presentation.events.TaskFormState
import com.example.paceto_doapp.viewModel.TaskFormScreenVM
import java.time.LocalDateTime

// 1. The Stateful Wrapper (Wire this to your NavGraph)
@Composable
fun TaskFormScreen(
    viewModel: TaskFormScreenVM = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    TaskFormContent(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack
    )
}

// 2. The Stateless UI (Safe for Previews!)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormContent(
    state: TaskFormState,
    onEvent: (TaskFormEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Create Task",
                        fontWeight = FontWeight.Bold
                    )
                },
                windowInsets = WindowInsets(0.dp),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
            )
        },
        // The Pinned Bottom Button!
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = {
                        onEvent(TaskFormEvent.SaveTask(onSuccessfulSave = onNavigateBack))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Create Task"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create Task")
                    }
                }
            }
        }
    ) { innerPadding ->

        // THIS is the column that scrolls
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Ensures it doesn't hide behind the bars
                .verticalScroll(scrollState) // Makes the content scrollable!
        ) {

            // Text Fields
            TaskTextField(
                value = state.title,
                onValueChange = { onEvent(TaskFormEvent.TitleChanged(it)) },
                label = "Task Title",
                placeholder = "What needs to be done?",
                isError = state.titleError,
                errorMessage = state.titleErrorMessage
            )

            TaskTextField(
                value = state.description,
                onValueChange = { onEvent(TaskFormEvent.DescriptionChanged(it)) },
                label = "Description",
                placeholder = "Add some details about this task...",
                singleLine = false,
                minLines = 4,
                maxLength = 500
            )

            // Schedule Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DatePickerField(
                        label = "Start Date",
                        selectedDate = state.startDate,
                        onClick = { showStartDatePicker = true }
                    )
                    DatePickerField(
                        label = "End Date",
                        selectedDate = state.endDate,
                        onClick = { showEndDatePicker = true }
                    )
                }
            }

            // Category Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                CategorizationDropdown(
                    selectedCategory = state.category,
                    onCategorySelected = { newCategory ->
                        onEvent(TaskFormEvent.CategorySelected(newCategory))
                    }
                )
            }

            // Added a little spacer at the bottom so the last card doesn't
            // sit directly on top of the "Create Task" button when scrolled down
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Dialogs remain at the bottom
    PickTaskDate(
        isOpen = showStartDatePicker,
        onDismiss = { showStartDatePicker = false },
        onConfirm = { pickedDate ->
            onEvent(TaskFormEvent.StartDateChanged(pickedDate))
        }
    )

    PickTaskDate(
        isOpen = showEndDatePicker,
        onDismiss = { showEndDatePicker = false },
        onConfirm = { pickedDate ->
            onEvent(TaskFormEvent.EndDateChanged(pickedDate))
        }
    )
}

// 3. The Preview (Now feeds dummy data to the stateless content)
@Preview(showBackground = true)
@Composable
fun TaskFormScreenPreview() {
    val dummyState = TaskFormState(
        topBarTitle = "Create New Task",
        title = "",
        description = "",
        startDate = LocalDateTime.now(),
        endDate = LocalDateTime.now().plusHours(1),
        category = Category.FOCUS,
        titleError = false
    )

    TaskFormContent(
        state = dummyState,
        onEvent = {},
        onNavigateBack = {}
    )
}