package com.universidad.gymclass.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object ClassDetailScreen : Screen("class_detail_screen/{classId}") {
        fun createRoute(classId: String) = "class_detail_screen/$classId"
    }
}