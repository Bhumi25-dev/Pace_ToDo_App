package com.okay.paceto_doapp.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.okay.paceto_doapp.data.repo.TaskRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotificationReceiverEntryPoint {
    fun taskRepository(): TaskRepo
}

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)?.takeIf { it.isNotBlank() } ?: return
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when (intent.action) {
            ACTION_MARK_COMPLETE -> {
                notificationManager.cancel(taskId.hashCode())
                TaskCountdownService.requestStop(taskId)

                val pendingResult = goAsync()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val entryPoint = EntryPointAccessors.fromApplication(
                            context.applicationContext,
                            NotificationReceiverEntryPoint::class.java
                        )
                        val repository = entryPoint.taskRepository()
                        repository.updateTaskIsCompleted(
                            taskId = taskId,
                            isCompleted = true,
                            completeDate = LocalDate.now()
                        )
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to mark task $taskId complete", e)
                    } finally {
                        pendingResult.finish()
                    }
                }
            }
            ACTION_DISMISS -> {
                notificationManager.cancel(taskId.hashCode())
                TaskCountdownService.requestStop(taskId)
            }
        }
    }

    companion object {
        private const val TAG = "NotificationActionReceiver"
        const val ACTION_MARK_COMPLETE = "com.okay.paceto_doapp.MARK_COMPLETE"
        const val ACTION_DISMISS = "com.okay.paceto_doapp.DISMISS"
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}