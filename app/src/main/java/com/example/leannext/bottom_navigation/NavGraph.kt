package com.example.leannext.bottom_navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.leannext.MainViewModel
import com.example.leannext.utlis.Constants

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Constants.DiagramScreen.route)
    {
        composable(Constants.TestScreen.route)
        {
            TestScreen(navHostController = navHostController)
        }
        composable(Constants.DiagramScreen.route)
        {
            DiagramScreen(navHostController = navHostController)
        }
        composable(Constants.ProfileScreen.route)
        {
            ProfileScreen(navHostController = navHostController)
        }
        composable(
            route = Constants.DirectionTestScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            var a = it.arguments?.getInt("id")
            if (a != null) {
                DirectionTestScreen(navHostController = navHostController,id = a)
            }
        }
    }
}