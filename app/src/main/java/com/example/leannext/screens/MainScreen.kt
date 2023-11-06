package com.example.leannext.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.leannext.bottom_navigation.BottomNavigation
import com.example.leannext.bottom_navigation.NavGraph

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.background),
        bottomBar = { BottomNavigation(navHostController = navHostController) }
    ) {
        NavGraph(navHostController = navHostController)
    }
}