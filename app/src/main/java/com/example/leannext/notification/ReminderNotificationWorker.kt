package com.example.leannext.notification

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Math.log
import java.util.Calendar
import java.util.concurrent.TimeUnit

class ReminderNotificationWorker(private val appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        NotificationHandler.createReminderNotification(appContext)
        return Result.success()
    }
    companion object {
        /**
         * @param hourOfDay the hour at which daily reminder notification should appear [0-23]
         * @param minute the minute at which daily reminder notification should appear [0-59]
         */
        fun schedule(appContext: Context, hourOfDay: Int, minute: Int) {
            Log.d("TAGNOTF","Reminder scheduling request received for $hourOfDay:$minute")
            println("schedule")
            Log.d("TAGNOTF","schedule")
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

            if (target.before(now)) {
                target.add(Calendar.DAY_OF_YEAR, 1)
            }

            Log.d("TAGNOTF","Scheduling reminder notification for ${target.timeInMillis - System.currentTimeMillis()} ms from now")

            val notificationRequest = PeriodicWorkRequestBuilder<ReminderNotificationWorker>(1, TimeUnit.HOURS)
                .addTag("TAG_REMINDER_WORKER")
                .setInitialDelay(target.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(appContext)
                .enqueueUniquePeriodicWork(
                    "reminder_notification_work",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    notificationRequest
                )
        }
    }
}