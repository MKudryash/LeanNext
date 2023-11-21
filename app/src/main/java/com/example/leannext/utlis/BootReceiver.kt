package com.example.leannext.utlis

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            var showPushNotification  = schedulePushNotifications(context)// implement showing notification in this function
            showPushNotification.schedulePushNotifications()
        }
    }
}