package com.okay.paceto_doapp.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okay.paceto_doapp.ui.icons.equalizer
import com.okay.paceto_doapp.ui.presentation.components.TimerUI
import com.okay.paceto_doapp.viewModel.PomodoroScreenVM
import com.okay.test.pause

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen(
    onNavigateBack: () -> Unit,
    viewModel : PomodoroScreenVM = hiltViewModel()
) {
    val focusTask by viewModel.currentTask.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var play by remember { mutableStateOf(false) }
    var resetNonce by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = uiState.isSavedSuccess) {
        if (uiState.isSavedSuccess) {
            viewModel.resetSaveState()
            onNavigateBack()
        }
    }

    val task = focusTask
    if (task == null) {
        // Handle loading phase safely while Room fetches from storage disk layers
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Focus Session",
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
                                imageVector = Icons.Default.Close,
                                contentDescription = "close session"
                            )
                        }
                    },
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.focusName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                TimerUI(
                    maxIndicatorValue = task.duration,
                    isPlaying = play,
                    restartTrigger = resetNonce,
                    onTimerFinished = {
                        play = false
                        viewModel.saveCompletedSession(focusTask = task)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                //Play Button
                PlayPauseButton(
                    play = play,
                    onClick = { play = !play }
                )

                Spacer(modifier = Modifier.height(4.dp))

                //Restart Button
                RestartButton(
                    onClick = {
                        play = false
                        resetNonce++
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BackgroundMusicCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = equalizer,
                    contentDescription = "Background Music",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Background Music",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PlayPauseButton(
    icon1 : ImageVector = pause,
    icon2 : ImageVector = Icons.Filled.PlayArrow,
    iconDescription : String = "Play or Pause" ,
    text1 : String = "Pause",
    text2 : String = "Play",
    onClick: () -> Unit,
    play: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = if (play) icon1 else icon2,
                contentDescription = iconDescription
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (play) text1 else text2,
            )
        }
    }
}

@Composable
fun RestartButton(
    icon : ImageVector = Icons.Default.Refresh,
    iconDescription: String = "Restart Ritual",
    text : String = "Restart",
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
            )
        }
    }
}