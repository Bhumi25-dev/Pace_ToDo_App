package com.example.paceto_doapp.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.paceto_doapp.data.repo.TaskRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
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
        val taskId = intent.getStringExtra(EXTRA_TASK_ID) ?: return
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when (intent.action) {
            ACTION_MARK_COMPLETE -> {
                val entryPoint = EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    NotificationReceiverEntryPoint::class.java
                )
                val repository = entryPoint.taskRepository()

                CoroutineScope(Dispatchers.IO).launch {
                    repository.updateTaskIsCompleted(
                        taskId = taskId,
                        isCompleted = true,
                        completeDate = LocalDate.now()
                    )
                }
                notificationManager.cancel(taskId.hashCode())
            }
            ACTION_DISMISS -> {
                notificationManager.cancel(taskId.hashCode())
                val stopIntent = Intent(context, TaskCountdownService::class.java).apply {
                    action = TaskCountdownService.ACTION_STOP
                    putExtra(TaskCountdownService.EXTRA_TASK_ID, taskId)
                }
                context.startService(stopIntent)
            }
        }
    }

    companion object {
        const val ACTION_MARK_COMPLETE = "com.example.paceto_doapp.MARK_COMPLETE"
        const val ACTION_DISMISS = "com.example.paceto_doapp.DISMISS"
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}