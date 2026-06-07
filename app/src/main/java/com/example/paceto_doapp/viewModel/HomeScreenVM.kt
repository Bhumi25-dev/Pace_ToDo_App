package com.example.paceto_doapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paceto_doapp.data.repo.TaskRepo
import com.example.paceto_doapp.ui.presentation.events.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val repo: TaskRepo
): ViewModel() {

    val state: StateFlow<HomeScreenState> = repo.getAllTasks()
        .map { tasks ->
            HomeScreenState(
                totalTasks = tasks.size,
                completedTasks = tasks.count { it.isCompleted },
                pendingTasksList = tasks.filter { !it.isCompleted }
            )
        } .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

}