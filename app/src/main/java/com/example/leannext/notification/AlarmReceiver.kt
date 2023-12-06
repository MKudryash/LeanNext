package com.example.leannext.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.leannext.MainActivity
import com.example.leannext.R
import java.time.LocalDateTime
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        println("onReceive")
        Log.d("TAGNOTF","onReceive")
        if (context != null) {
            ReminderNotificationWorker.schedule(context, LocalDateTime.now().hour, LocalDateTime.now().minute+1)
        }
    }
}