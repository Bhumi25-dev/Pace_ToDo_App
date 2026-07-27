package com.okay.paceto_doapp.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.local.Task
import com.okay.paceto_doapp.data.repo.TaskRepo
import com.okay.paceto_doapp.notification.TaskCountdownService
import com.okay.paceto_doapp.ui.presentation.events.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: TaskRepo
) : ViewModel() {

    private val allTasks: StateFlow<List<Task>> = repo.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val state: StateFlow<HomeScreenState> = allTasks
        .map { tasks ->
            HomeScreenState(
                totalTasks = tasks.size,
                completedTasks = tasks.count { it.isCompleted },
                pendingTasksList = tasks.filter { !it.isCompleted }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

    init {
        checkAndStartCountdowns()
    }

    private fun checkAndStartCountdowns() {
        viewModelScope.launch {
            allTasks.collect { tasks ->
                val now = LocalDateTime.now()
                val tenHoursFromNow = now.plusHours(10)

                tasks.filter { task ->
                    !task.isCompleted &&
                            task.endDate.isAfter(now) &&
                            task.endDate.isBefore(tenHoursFromNow)
                }.forEach { task ->
                    startTaskCountdown(task)
                }
            }
        }
    }

    private fun startTaskCountdown(task: Task) {
        val intent = Intent(context, TaskCountdownService::class.java).apply {
            action = TaskCountdownService.ACTION_START
            putExtra(TaskCountdownService.EXTRA_TASK_ID, task.id)
        }
        context.startForegroundService(intent)
    }
}