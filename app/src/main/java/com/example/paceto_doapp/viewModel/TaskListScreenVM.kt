package com.example.paceto_doapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.repo.TaskRepo
import com.example.paceto_doapp.ui.presentation.events.TaskEvent
import com.example.paceto_doapp.ui.presentation.events.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskListScreenVM @Inject constructor(
    private val repo: TaskRepo
): ViewModel() {

    private val _categoryFilter: MutableStateFlow<Category?> = MutableStateFlow(null)

    val state: StateFlow<TaskState> = combine(_categoryFilter,repo.getAllTasks()){ filter, tasks ->
        if (filter == null)
            TaskState(
                tasks = tasks,
                selectedCategory = null
            )
        else
            TaskState(
                tasks = tasks.filter{ it.category == filter},
                selectedCategory = filter
            )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskState()
    )

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.DeleteTask -> viewModelScope.launch {
                repo.deleteTask(event.task)
            }
            is TaskEvent.UpdateTaskIsCompleted -> viewModelScope.launch {

                val completionDate = if (event.isCompleted) LocalDate.now() else null

                repo.updateTaskIsCompleted(
                    taskId = event.taskId,
                    isCompleted = event.isCompleted,
                    completeDate = completionDate
                )
            }
            is TaskEvent.SetCategoryFilter -> _categoryFilter.value = event.category
        }
    }
}