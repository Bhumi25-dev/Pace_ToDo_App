package com.okay.paceto_doapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.okay.paceto_doapp.data.local.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PendingNotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showTaskCountdownNotification(task: Task, remainingSeconds: Long) {
        val hours = remainingSeconds / 3600
        val minutes = (remainingSeconds % 3600) / 60
        val seconds = remainingSeconds % 60 // Fix: was incorrectly calling remainingSeconds(task)

        val timeString = if (hours > 0) {
            String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.US,"%02d:%02d", minutes, seconds)
        }

        val isUrgent = remainingSeconds <= 300

        val contentText = if (isUrgent) "Critical deadline approaching..." else "Task due soon..."

        val markCompleteIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_MARK_COMPLETE
            putExtra(NotificationActionReceiver.EXTRA_TASK_ID, task.id)
        }
        val markCompletePendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            markCompleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_DISMISS
            putExtra(NotificationActionReceiver.EXTRA_TASK_ID, task.id)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.hashCode() + 1,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, PENDING_CHANNEL_ID)
            .setSmallIcon(getNotificationIcon(task.category)) // reuses your existing util
            .setContentTitle(task.title)
            .setContentText(contentText)
            .setSubText(timeString)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(0, "Dismiss", dismissPendingIntent)
            .addAction(0, "Mark as Completed", markCompletePendingIntent)
            .build()

        notificationManager.notify(task.id.hashCode(), notification)
    }

    fun cancelNotification(taskId: String) {
        notificationManager.cancel(taskId.hashCode())
    }

    companion object {
        const val PENDING_CHANNEL_ID = "Pending_task_timer"
    }
}