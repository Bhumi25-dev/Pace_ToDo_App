package com.okay.paceto_doapp.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okay.paceto_doapp.ui.presentation.components.FormTextField
import com.okay.paceto_doapp.ui.presentation.events.ProfileEvent
import com.okay.paceto_doapp.ui.presentation.viewModel.ProfileVM
import java.sql.Types.NULL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    viewModel: ProfileVM = hiltViewModel(),
    onNavigateBack: () -> Unit,
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
                            text = "Edit Profile",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    windowInsets = WindowInsets(0),
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
                )
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.onEvent(ProfileEvent.SaveProfile(onNavigateBack))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                enabled = state.name.isNotBlank() && state.age > 0 && state.profession.isNotBlank() && state.email.isNotBlank()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("Save Changes")
                }
            }
        }
    ) {innerPadding ->

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))

            FormTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(ProfileEvent.NameChanged(it))},
                label = "Username",
                placeholder = "Enter Your name",
            )

            FormTextField(
                value = state.age.toString(),
                onValueChange = {input->
                    val age = input.toIntOrNull()
                    if(age!=NULL) viewModel.onEvent(ProfileEvent.AgeChanged(age))
                },
                label = "Age",
                placeholder = "Eg.: 18,21,etc.",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            FormTextField(
                value = state.profession,
                onValueChange = { viewModel.onEvent((ProfileEvent.ProfessionChanged(it))) },
                label = "Profession",
                placeholder = "Eg.: Student,Developer,etc.",
            )

            FormTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(ProfileEvent.EmailChanged(it)) },
                label = "Email",
                placeholder = "someone@example.com"
            )
        }

    }

}