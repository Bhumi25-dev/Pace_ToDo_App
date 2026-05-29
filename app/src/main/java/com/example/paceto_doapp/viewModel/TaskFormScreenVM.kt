package com.example.paceto_doapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paceto_doapp.data.local.Task
import com.example.paceto_doapp.ui.presentation.events.TaskFormEvent
import com.example.paceto_doapp.data.repo.TaskRepo
import com.example.paceto_doapp.ui.presentation.events.TaskFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskFormScreenVM @Inject constructor(
    private val repo: TaskRepo,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle //to catch pre-existing task's id for edit
): ViewModel() {

    private val _state : MutableStateFlow<TaskFormState> = MutableStateFlow(TaskFormState())
    val state = _state.asStateFlow()

    init {
        val taskID = savedStateHandle.get<String>("taskId")
        if(taskID!=null){
            loadTaskForEditing(taskID)
        }
    }

    private fun loadTaskForEditing(taskID: String) {
        viewModelScope.launch {
            val existingTask = repo.getTaskByTaskId(taskID)

            if (existingTask != null) {
                _state.update {
                    it.copy(
                        id = existingTask.id,
                        topBarTitle = "Edit Task",
                        title = existingTask.title,
                        description = existingTask.description,
                        startDate = existingTask.startDate,
                        endDate = existingTask.endDate,
                        category = existingTask.category,
                        priority = existingTask.priority
                    )
                }
            }
        }
    }

    fun onEvent(event: TaskFormEvent) {
        when (event){
            is TaskFormEvent.TitleChanged -> _state.update { it.copy(title = event.title, titleError = false) }
            is TaskFormEvent.DescriptionChanged -> _state.update { it.copy(description = event.des) }
            is TaskFormEvent.StartDateChanged -> _state.update { it.copy(startDate = event.date) }
            is TaskFormEvent.EndDateChanged -> _state.update { it.copy(endDate = event.date) }
            is TaskFormEvent.CategorySelected -> _state.update { it.copy(category = event.category) }
            is TaskFormEvent.PrioritySelected -> _state.update { it.copy(priority = event.priority) }
            is TaskFormEvent.SaveTask -> saveTask(event.onSuccessfulSave)
            is TaskFormEvent.CancelTask -> {
                //TODO: Navigate back to the task list screen
            }
        }
    }

    private fun saveTask(onSuccess: () -> Unit){
        viewModelScope.launch {
            val currentState = state.value

            if(currentState.title.isBlank()){
                _state.update {
                    it.copy(
                        titleError = true,
                        titleErrorMessage = "Please Enter a valid Title"
                    )
                }
                return@launch
            }

            withContext(NonCancellable) {
                val newTask: Task = Task(
                    id = currentState.id ?: UUID.randomUUID().toString(),
                    title = currentState.title,
                    description = currentState.description,
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    category = currentState.category,
                    priority = currentState.priority,
                )

                repo.createTask(newTask)

                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Task created successfully", Toast.LENGTH_SHORT).show()
                    onSuccess()
                }
            }
        }
    }
}