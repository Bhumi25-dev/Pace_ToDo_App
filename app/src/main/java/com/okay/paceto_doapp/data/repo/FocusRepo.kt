package com.okay.paceto_doapp.data.repo

import com.okay.paceto_doapp.data.local.FocusDao
import com.okay.paceto_doapp.data.local.FocusRecords
import com.okay.paceto_doapp.data.local.FocusTask
import com.okay.paceto_doapp.data.local.FocusTaskWithRecords
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusRepo @Inject constructor(
    private val localDao: FocusDao
){

    suspend fun updateFocusTask(focusTask: FocusTask)
    {
        localDao.updateFocusTask(task = focusTask)
    }

    suspend fun deleteFocusTask(focusName: String)
    {
        localDao.deleteFocusTask(focusName = focusName)
    }

    suspend fun getFocusTaskByName(focusName: String) : FocusTask
    {
        return localDao.getFocusTaskByName(focusName = focusName)
    }

    suspend fun saveOrIncrementRecord(focusName: String, durationMinutes: Int)
    {
        val today = LocalDate.now()
        val existingRecord = localDao.getRecordForDay(today,focusName)

        if(existingRecord != null)
        {
            val updatedRecord = existingRecord.copy(
                duration = existingRecord.duration + durationMinutes,
                sessionCount = existingRecord.sessionCount + 1
            )
            localDao.updateRecord(updatedRecord)
        } else {
            val newRecord = FocusRecords(
                date = today,
                focusName = focusName,
                duration = durationMinutes,
                sessionCount = 1
            )
            localDao.insertRecord(newRecord)
        }
    }

    fun getAllFocusTasksWithRecords() : Flow<List<FocusTaskWithRecords>> {
        return localDao.getAllFocusTaskWithRecords()
    }
}