package com.okay.paceto_doapp.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okay.paceto_doapp.ui.presentation.components.ProfileCard
import com.okay.paceto_doapp.ui.presentation.components.ProfileDataTile
import com.okay.paceto_doapp.viewModel.ProfileVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDisplayScreen(
    viewModel: ProfileVM = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
                        Text(
                            text = "Your Profile",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    windowInsets = WindowInsets(0.dp),
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    ),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Search"
                            )
                        }
                    },
                )
            }
        }
    ) {innerPadding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ProfileCard(state.name, state.profession)

            Spacer(modifier = Modifier.height(16.dp))

            ProfileDataTile(Icons.Default.DateRange,"Age",state.age.toString())
            ProfileDataTile(Icons.Default.Email,"Email",state.email)

        }

    }
}