package com.example.leannext.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leannext.screens.MainScreen
import com.example.leannext.screens.SplashScreen
import com.example.leannext.viewModel.MainViewModel
/**Навгиция после запуска SplachScreen*/
@Composable
fun Navigation(viewModel: MainViewModel) {
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
            MainScreen(viewModel)
        }
    }
}