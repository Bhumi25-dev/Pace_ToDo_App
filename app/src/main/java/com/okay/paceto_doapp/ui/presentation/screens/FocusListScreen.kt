package com.okay.paceto_doapp.ui.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.local.durations
import com.okay.paceto_doapp.ui.presentation.components.FocusCard
import com.okay.paceto_doapp.ui.presentation.components.FocusFormBottomSheet
import com.okay.paceto_doapp.ui.presentation.components.GenericFilterBar
import com.okay.paceto_doapp.ui.presentation.events.FocusListEvent
import com.okay.paceto_doapp.viewModel.FocusListVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusListScreen(
    viewModel: FocusListVM = hiltViewModel(),
    onRitualCardClick: (FocusTask) -> Unit
) {

    val records by viewModel.state.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<FocusTask?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val isFabVisible by remember {
        derivedStateOf {
            !listState.isScrollInProgress || listState.firstVisibleItemIndex == 0
        }
    }

    // Single screen-level animation clock — all FocusCards share this one Float
    val cardGlowTransition = rememberInfiniteTransition(label = "focus_list_glow")
    val cardGlowAlpha by cardGlowTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card_glow_alpha"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                )
            ) {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("Build Your ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("Ritual")
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    ),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    windowInsets = WindowInsets(0.dp),
                    actions = {
                        Surface(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onErrorContainer),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Category Icon",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                )
                GenericFilterBar(
                    items = durations,
                    selectedItem = null,
                    onItemSelect = {},
                    labelProvider = { it?.name ?: "All" },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFabVisible,
                enter = slideInHorizontally ( initialOffsetX = { it } ),
                exit = slideOutHorizontally ( targetOffsetX = { it } )
            ) {
                SmallFloatingActionButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 80.dp),
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
            modifier = Modifier
                .fillMaxSize(),
            // Add 96dp at the bottom so the last card clears the floating nav bar
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding() + 96.dp
            )
        ) {
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Small steps lead to significant breakthroughs.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            items(records.focusTasks) { item ->
                FocusCard(
                    item = item,
                    glowAlpha = cardGlowAlpha,
                    onEditClick = { _ ->
                        selectedTask = item.task
                        showBottomSheet = true
                    },
                    onDeleteClick = { viewModel.onEvent(FocusListEvent.DeleteFocusTask(item.task.focusName)) },
                    onCardClick = { onRitualCardClick(item.task) }
                )
            }
        }
    }

    if(showBottomSheet)
    {
        FocusFormBottomSheet(
            focusTask = selectedTask,
            discardRitual = {
                showBottomSheet = false
                selectedTask = null
            },
            sheetState = sheetState,
            onSaveRitual = { (focusName, durationName, duration) ->
                viewModel.onEvent(FocusListEvent.UpdateFocusTask(focusName, durationName, duration))
                showBottomSheet = false
                selectedTask = null
            }
        )
    }

}
