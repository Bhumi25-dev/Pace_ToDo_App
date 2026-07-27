package com.okay.paceto_doapp.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.repo.FocusRepo
import com.okay.paceto_doapp.ui.presentation.events.PomodoroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PomodoroScreenVM @Inject constructor(
    private val focusRepo: FocusRepo,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val focusName: String = checkNotNull(savedStateHandle["focusName"])
    private val _currentTask = MutableStateFlow<FocusTask?>(null)
    val currentTask = _currentTask.asStateFlow()

    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTaskDetails()
    }

    private fun loadTaskDetails()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val task = focusRepo.getFocusTaskByName(focusName)
            _currentTask.value = task
        }
    }

    fun saveCompletedSession(focusTask: FocusTask)
    {
        if(_uiState.value.isSaving) return

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch(Dispatchers.IO){
            try{
                focusRepo.saveOrIncrementRecord(
                    focusName = focusTask.focusName,
                    durationMinutes = focusTask.duration
                )

                _uiState.update { it.copy(isSaving = false, isSavedSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

    fun resetSaveState() {
        _uiState.update { PomodoroUiState() }
    }
}