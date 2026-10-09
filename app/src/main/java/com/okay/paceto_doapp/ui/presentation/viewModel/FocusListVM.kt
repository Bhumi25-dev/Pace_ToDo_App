package com.okay.paceto_doapp.ui.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.local.Duration
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.local.FocusTaskWithRecords
import com.okay.paceto_doapp.data.repo.FocusRepo
import com.okay.paceto_doapp.ui.presentation.events.FocusListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FocusListState(
    val focusTasks: List<FocusTaskWithRecords> = emptyList(),
    val selectedDuration: Duration? = null,
    val loading: Boolean = false
)

@HiltViewModel
class FocusListVM @Inject constructor(
    private val focusRepo: FocusRepo
): ViewModel() {

    private val _durationFilter: MutableStateFlow<Duration?> = MutableStateFlow(null)

    val state: StateFlow<FocusListState> = combine(focusRepo.getAllFocusTasksWithRecords(),_durationFilter)
    { combinedList, selectedDuration ->
        val filteredList = if (selectedDuration==null)
        {
            combinedList
        } else {
            combinedList.filter { it.task.duration == selectedDuration.durationMin }
        }

        FocusListState(
            focusTasks = filteredList,
            selectedDuration = selectedDuration,
            loading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FocusListState(loading = true)
    )

    fun onEvent(event: FocusListEvent) {
        when (event) {
            is FocusListEvent.DeleteFocusTask -> {
                viewModelScope.launch {
                    focusRepo.deleteFocusTask(focusName = event.focusName)
                }
            }
            is FocusListEvent.UpdateFocusTask -> {
                viewModelScope.launch {
                    val newTask = FocusTask(event.focusName, event.durationName, event.duration)
                    focusRepo.updateFocusTask(newTask)
                }
            }
            is FocusListEvent.SetDurationFilter -> {
                _durationFilter.value = event.duration
            }
        }
    }
}