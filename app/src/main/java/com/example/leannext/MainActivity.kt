package com.example.leannext

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkManager
import com.example.leannext.Navigation.Navigation
import com.example.leannext.dataStore.StoreData
import com.example.leannext.notification.AlarmReceiver
import com.example.leannext.notification.PreferenceStore
import com.example.leannext.notification.ReminderNotificationWorker
import com.example.leannext.ui.theme.LeanNextTheme
import com.example.leannext.utlis.Constants
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.viewModel.MainViewModelFactory
import java.util.Calendar


class MainActivity : ComponentActivity() {
    lateinit var alarmManager: AlarmManager
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            LeanNextTheme {
                ActivityCompat.requestPermissions(
                    this, arrayOf(READ_EXTERNAL_STORAGE), 23
                )
                val dataStore = StoreData(LocalContext.current)
                val flag = dataStore.getData.collectAsState(initial = "")
                Constants.userName = flag.value

                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val owner = LocalViewModelStoreOwner.current
                scheduleNotification(LocalContext.current)
                owner?.let {
                    val viewModel: MainViewModel = viewModel(
                        it,
                        "MainViewModel",
                        MainViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )
                    viewModel.scheduleReminderNotification(12, 30)
                    Navigation(viewModel)
                }
            }
        }
    }
     private fun scheduleNotification(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + 3000 // 30 seconds from now
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,AlarmManager.INTERVAL_HOUR, pendingIntent)
    }

}

