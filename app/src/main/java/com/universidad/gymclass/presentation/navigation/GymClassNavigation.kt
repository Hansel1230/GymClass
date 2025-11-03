package com.universidad.gymclass.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.universidad.gymclass.presentation.auth.LoginScreen
import com.universidad.gymclass.presentation.auth.RegisterScreen
import com.universidad.gymclass.presentation.class_detail.ClassDetailScreen
import com.universidad.gymclass.presentation.home.HomeScreen

@Composable
fun GymClassNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen()
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen()
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.ClassDetailScreen.route,
            arguments = listOf(navArgument("classId") { type = NavType.StringType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("classId")
            requireNotNull(classId) { "classId parameter not found" }
            ClassDetailScreen(navController = navController, classId = classId)
        }
    }
}