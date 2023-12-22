package com.example.leannext.bottom_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.screens.DiagramScreen
import com.example.leannext.screens.DirectionTestScreen
import com.example.leannext.screens.MainScreen
import com.example.leannext.screens.ProfileScreen
import com.example.leannext.screens.TestScreen
import com.example.leannext.utlis.Constants
/*Класс для перемещения по страницам*/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navHostController: NavHostController,viewModel: MainViewModel
) {
    NavHost(navController = navHostController,  startDestination = Constants.DiagramScreen.route)
    {
        composable(Constants.TestScreen.route)
        {
            TestScreen(navHostController = navHostController, viewModel)
        }
        composable(Constants.DiagramScreen.route)
        {
            DiagramScreen(navHostController = navHostController,viewModel)
        }
        composable(Constants.ProfileScreen.route)
        {
            ProfileScreen(navHostController = navHostController)
        }
        composable(Constants.MainScreen.route)
        {
            MainScreen(viewModel)
        }
        composable(
            route = Constants.DirectionTestScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            var id = it.arguments?.getInt("id")
            var name = it.arguments?.getString("name")
            if (id != null && name!= null) {
                DirectionTestScreen(navHostController = navHostController, id = id, name = name,viewModel)
            }
        }
    }
}