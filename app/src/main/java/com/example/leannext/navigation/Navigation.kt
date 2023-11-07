package com.example.leannext.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leannext.screens.MainScreen
import com.example.leannext.screens.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen( navController = navController )
        }

        // Main Screen
        composable("main_screen") {
            MainScreen()
        }
    }
}