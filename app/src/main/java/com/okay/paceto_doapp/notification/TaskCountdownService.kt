package com.okay.paceto_doapp.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.okay.paceto_doapp.data.local.Task
import com.okay.paceto_doapp.data.repo.TaskRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class TaskCountdownService : Service() {

    @Inject
    lateinit var taskRepository: TaskRepo

    @Inject
    lateinit var notificationService: PendingNotificationService

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val activeJobs = mutableMapOf<String, Job>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val taskId = intent?.getStringExtra(EXTRA_TASK_ID)
        val notifId = taskId?.hashCode() ?: DEFAULT_NOTIFICATION_ID
        startForeground(notifId, getInitialForegroundNotification())

        if (taskId.isNullOrBlank()) {
            stopSelfSafely()
            return START_NOT_STICKY
        }

        when (intent.action) {
            ACTION_START -> {
                serviceScope.launch {
                    val task = taskRepository.getTaskByTaskId(taskId)
                    if (task == null || task.isCompleted) {
                        stopCountdownForTask(taskId)
                        return@launch
                    }
                    startCountdownForTask(task)
                }
            }
            ACTION_STOP -> {
                stopCountdownForTask(taskId)
            }
            else -> {
                stopSelfSafely()
            }
        }

        return START_NOT_STICKY
    }

    private fun startCountdownForTask(task: Task) {
        // Cancel any existing job for this task first
        activeJobs[task.id]?.cancel()

        val job = serviceScope.launch {
            while (true) {
                val remainingSeconds = ChronoUnit.SECONDS.between(
                    LocalDateTime.now(),
                    task.endDate
                )

                if (remainingSeconds <= 0) {
                    // Time's up — cancel notification and stop
                    notificationService.cancelNotification(task.id)
                    stopCountdownForTask(task.id)
                    break
                }

                notificationService.showTaskCountdownNotification(task, remainingSeconds)
                delay(1000L)
            }
        }

        activeJobs[task.id] = job

        val remainingNow = ChronoUnit.SECONDS.between(LocalDateTime.now(), task.endDate)
            .coerceAtLeast(0L)
        notificationService.showTaskCountdownNotification(task, remainingNow)
    }

    private fun getInitialForegroundNotification() =
        androidx.core.app.NotificationCompat.Builder(this, PendingNotificationService.PENDING_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Task Countdown")
            .setContentText("Syncing deadline timer...")
            .setOngoing(true)
            .setSilent(true)
            .build()

    private fun stopCountdownForTask(taskId: String) {
        activeJobs[taskId]?.cancel()
        activeJobs.remove(taskId)
        notificationService.cancelNotification(taskId)

        stopSelfSafely()
    }

    private fun stopSelfSafely() {
        if (activeJobs.isEmpty()) {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    override fun onTimeout(startId: Int) {
        super.onTimeout(startId)
        serviceScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
        const val ACTION_START = "com.okay.paceto_doapp.START_COUNTDOWN"
        const val ACTION_STOP = "com.okay.paceto_doapp.STOP_COUNTDOWN"
        private const val DEFAULT_NOTIFICATION_ID = 99901
    }
}