package com.example.leannext.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph (
    navHostController:NavHostController
){
    NavHost(navController = navHostController, startDestination = "diagram" )
    {
        composable("test")
        {
            TestScreen()
        }
        composable("diagram")
        {
            DiagramScreen()
        }
        composable("profile")
        {
            ProfileScreen()
        }
    }
}