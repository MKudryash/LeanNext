package com.example.leannext

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leannext.Navigation.Navigation
import com.example.leannext.ui.theme.LeanNextTheme
import com.example.leannext.utlis.ExportDataToCsv
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.viewModel.MainViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

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
