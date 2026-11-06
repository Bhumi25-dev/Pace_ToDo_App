package com.okay.paceto_doapp.ui.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.repo.TaskRepo
import com.okay.paceto_doapp.ui.presentation.events.CalendarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CalendarVM @Inject constructor(
    private val repo: TaskRepo
): ViewModel() {

    private val _state = MutableStateFlow(CalendarUiState())
    val state = _state.asStateFlow()

    init {
        loadTasksForMonth(date=LocalDateTime.now())
    }

    private fun loadTasksForMonth(date: LocalDateTime)
    {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val startDate: LocalDateTime = date
                .toLocalDate()
                .withDayOfMonth(1)
                .atStartOfDay()
            val endDate: LocalDateTime = date
                .toLocalDate()
                .withDayOfMonth(date.toLocalDate().lengthOfMonth())
                .atTime(LocalTime.MAX)

            repo.getTasksForTimeRange(startDate = startDate, endDate = endDate)
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
                selectedDateTasks = state.value.tasksByDate[date] ?: emptyList()
            )
        }
    }

    fun onMonthScrolled(date: LocalDateTime)
    {
        loadTasksForMonth(date)
    }
}