package com.okay.paceto_doapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task ORDER BY isCompleted ASC, endDate ASC")
    fun getAllTasks(): Flow<List<Task>> //we use flow so that the ui gets updated as soon as there is a change in the db

    @Query("SELECT * FROM task WHERE id =:taskId")
    suspend fun getTaskById(taskId: String): Task?

    @Query("SELECT * FROM task WHERE category = :category ORDER BY endDate ASC")
    fun getTaskByCategory(category: Category): Flow<List<Task>>

    @Query("UPDATE task SET isCompleted = :isCompleted, taskCompleteDate = :completeDate WHERE id = :taskId")
    suspend fun updateTaskIsCompleted(taskId: String, isCompleted: Boolean, completeDate: LocalDate?)

    @Query("SELECT * FROM task WHERE endDate BETWEEN :startDate AND :endDate ORDER BY endDate ASC")
    fun getTasksForTimeRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Task>>

}