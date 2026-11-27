package com.okay.paceto_doapp.ui.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.local.Task
import com.okay.paceto_doapp.data.repo.TaskRepo
import com.okay.paceto_doapp.notification.TaskCountdownService
import com.okay.paceto_doapp.ui.presentation.events.CalendarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CalendarVM @Inject constructor(
    private val repo: TaskRepo
): ViewModel() {

    private val _state = MutableStateFlow(CalendarUiState())
    val state = _state.asStateFlow()

    private val visibleMonth = MutableStateFlow(YearMonth.now())

    init {
        observeVisibleMonthTasks()
    }

    private fun observeVisibleMonthTasks() {
        viewModelScope.launch {
            visibleMonth
                .flatMapLatest { month ->
                    _state.update { it.copy(isLoading = true) }
                    val startDate = month.atDay(1).atStartOfDay()
                    val endDate = month.atEndOfMonth().atTime(LocalTime.MAX)
                    repo.getTasksForTimeRange(startDate = startDate, endDate = endDate)
                }
                .collect { taskList ->
                    val groupedMap = taskList.groupBy { it.endDate.toLocalDate() }

                    _state.update { currentState ->
                        currentState.copy(
                            tasksByDate = groupedMap,
                            selectedDateTasks = groupedMap[currentState.selectedDate]
                                ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onDateSelected(date: LocalDate)
    {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = date,
                selectedDateTasks = currentState.tasksByDate[date] ?: emptyList()
            )
        }
    }

    fun onMonthScrolled(month: YearMonth)
    {
        visibleMonth.value = month
    }

    fun toggleTaskCompleted(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            val completionDate = if (isCompleted) LocalDate.now() else null
            repo.updateTaskIsCompleted(
                taskId = taskId,
                isCompleted = isCompleted,
                completeDate = completionDate
            )
            if (isCompleted) {
                TaskCountdownService.requestStop(taskId)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repo.deleteTask(task)
            TaskCountdownService.requestStop(task.id)
        }
    }
}