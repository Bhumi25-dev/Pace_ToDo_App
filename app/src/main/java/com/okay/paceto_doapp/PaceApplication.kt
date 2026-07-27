package com.okay.paceto_doapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.okay.paceto_doapp.notification.PendingNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Fix: was <=
            val channel = NotificationChannel(
                PendingNotificationService.PENDING_CHANNEL_ID,
                "Pending Task Timer",
                NotificationManager.IMPORTANCE_HIGH // HIGH so heads-up notification appears
            ).apply {
                description = "Countdown alerts for tasks due soon"
                enableVibration(true)
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}