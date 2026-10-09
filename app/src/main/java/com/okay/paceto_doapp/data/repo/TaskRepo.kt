package com.okay.paceto_doapp.data.repo

import com.okay.paceto_doapp.data.local.Category
import com.okay.paceto_doapp.data.local.Task
import com.okay.paceto_doapp.data.local.TaskDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
    suspend fun updateTaskIsCompleted(taskId: String, isCompleted: Boolean, completeDate: LocalDate?){
        localDao.updateTaskIsCompleted(taskId, isCompleted,completeDate)
    }

    fun getTasksForTimeRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Task>>{
        return localDao.getTasksForTimeRange(startDate,endDate)
    }
}
