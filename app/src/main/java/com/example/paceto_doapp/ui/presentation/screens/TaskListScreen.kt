package com.example.paceto_doapp.ui.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.paceto_doapp.ui.presentation.components.TaskDetailsCard
import com.example.paceto_doapp.ui.presentation.components.TaskScreenCard
import com.example.paceto_doapp.ui.presentation.events.TaskEvent
import com.example.paceto_doapp.viewModel.TaskListScreenVM
import com.skydoves.cloudy.cloudy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListScreenVM = hiltViewModel(),
    onAddTaskClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // STATE: Tracks the id of the selected task
    var selectedTaskById by remember { mutableStateOf<String?>(null) }
    //Dynamically find the fresh task from your state list
    val selectedTask = state.tasks.find { it.id == selectedTaskById }

    // ANIMATION: Smoothly transitions the blur radius
    val blurRadius by animateDpAsState(
        targetValue = if (selectedTask != null) 16.dp else 0.dp,
        label = "background_blur"
    )

    val listState = rememberLazyListState()

    val isFabVisible by remember {
        derivedStateOf {
            !listState.isScrollInProgress || listState.firstVisibleItemIndex == 0
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "My Tasks",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    windowInsets = WindowInsets(0.dp),
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    ),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = isFabVisible,
                    enter = slideInHorizontally ( initialOffsetX = { it } ),
                    exit = slideOutHorizontally ( targetOffsetX = { it } )
                ) {
                    SmallFloatingActionButton(
                        onClick = onAddTaskClick,
                        modifier = Modifier
                            .navigationBarsPadding()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Ritual"
                        )
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding() + 96.dp
                )
            ) {
                items(state.tasks) { task ->
                    TaskScreenCard(
                        task = task,
                        onCardClick = {
                            selectedTaskById = task.id
                        }
                    )
                }
            }
        }

        selectedTask?.let { task ->
            Dialog(
                onDismissRequest = { selectedTaskById = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                TaskDetailsCard(
                    task = task,
                    onNavigateBack = { selectedTaskById = null },
                    onMarkedAsComplete = {
                        viewModel.onEvent(TaskEvent.UpdateTaskIsCompleted(task.id, !task.isCompleted))
                    },
                    onEditClick = {
                        selectedTaskById = null
                        onEditClick(task.id)
                    },
                    onDeleteClick = {
                        viewModel.onEvent(TaskEvent.DeleteTask(task))
                        selectedTaskById = null
                    },
                )
            }
        }

    }
}