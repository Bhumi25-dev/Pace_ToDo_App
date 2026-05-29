package com.example.paceto_doapp.data.repo

import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Task
import com.example.paceto_doapp.data.local.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepo @Inject constructor(
    private val localDao: TaskDao
) {

    //to create and save task
    suspend fun createTask(task: Task){
        localDao.upsertTask(task)
    }

    //to delete task
    suspend fun deleteTask(task: Task){
        localDao.deleteTask(task)
    }

    //get all the tasks
    fun getAllTasks(): Flow<List<Task>>{
        return localDao.getAllTasks()
    }

    //to get task by id
    suspend fun getTaskByTaskId(taskId: String): Task?{
        return localDao.getTaskById(taskId= taskId)
    }

    //get tasks by category
    fun getTasksByCategory(category: Category): Flow<List<Task>>{
        return localDao.getTaskByCategory(category = category)
    }

    //update isCompleted
    suspend fun updateTaskIsCompleted(taskId: String, isCompleted: Boolean){
        localDao.updateTaskIsCompleted(taskId, isCompleted)
    }
}
