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
import com.universidad.gymclass.presentation.reservations.MyReservationsScreen
import com.universidad.gymclass.presentation.settings.SettingsScreen
import java.util.Date

@Composable
fun GymClassNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.MyReservationsScreen.route) {
            MyReservationsScreen(navController = navController)
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.ClassDetailScreen.route,
            arguments = listOf(
                navArgument("classId") { type = NavType.StringType },
                navArgument("reservationId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("classDate") {
                    type = NavType.LongType
                    defaultValue = Date().time
                }
            )
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("classId")
            requireNotNull(classId) { "classId parameter not found" }
            val reservationId = backStackEntry.arguments?.getString("reservationId")
            val classDateTime = backStackEntry.arguments?.getLong("classDate") ?: Date().time
            val classDate = Date(classDateTime)

            ClassDetailScreen(
                navController = navController,
                classId = classId,
                reservationId = if (reservationId == "null") null else reservationId,
                classDate = classDate
            )
        }
    }
}