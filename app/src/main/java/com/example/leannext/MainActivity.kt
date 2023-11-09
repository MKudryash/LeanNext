package com.example.leannext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.leannext.Navigation.Navigation
import com.example.leannext.ui.theme.LeanNextTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeanNextTheme {
                Navigation()
            }
        }
    }
}
