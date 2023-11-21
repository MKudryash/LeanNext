package com.example.leannext

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leannext.Navigation.Navigation
import com.example.leannext.ui.theme.LeanNextTheme
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.viewModel.MainViewModelFactory
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeanNextTheme {
                ActivityCompat.requestPermissions(
                    this, arrayOf(READ_EXTERNAL_STORAGE), 23
                )

                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: MainViewModel = viewModel(
                        it,
                        "MainViewModel",
                        MainViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )
                    Navigation(viewModel)
                }
            }
        }
    }
}
