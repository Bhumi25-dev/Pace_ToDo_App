package com.okay.paceto_doapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusDao {

    @Upsert
    suspend fun updateFocusTask(task: FocusTask)

    @Query("DELETE FROM FocusTask WHERE focusName = :focusName")
    suspend fun deleteFocusTask(focusName: String)

    @Query("SELECT * FROM FocusTask")
    fun getFocusTasks(): Flow<List<FocusTask>>

    @Query("SELECT * FROM FocusTask WHERE focusName = :focusName")
    suspend fun getFocusTaskByName(focusName: String): FocusTask

    // 1. Checks if the session already exists
    @Query("SELECT * FROM FocusRecords WHERE date = :date AND focusName = :focusName")
    suspend fun getRecordForDay(date: LocalDate, focusName: String): FocusRecords?

    // 2. If getRecordForDay returns null, insert a new record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: FocusRecords)

    // 3. Used to add counts to an existing record
    @Update
    suspend fun updateRecord(record: FocusRecords)

    // 4. Heat Map Stream
    @Query("SELECT * FROM FocusRecords WHERE focusName = :focusName AND date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getRecordsForHeatMap(
        focusName: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<FocusRecords>>

    @Transaction
    @Query("SELECT * FROM FocusTask")
    fun getAllFocusTaskWithRecords(): Flow<List<FocusTaskWithRecords>>
}