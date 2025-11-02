package com.universidad.gymclass.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.universidad.gymclass.presentation.auth.LoginScreen
import com.universidad.gymclass.presentation.auth.RegisterScreen

@Composable
fun GymClassNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen()
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen()
        }
        // Aquí se agregarán más destinos
    }
}