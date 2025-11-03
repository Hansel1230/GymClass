package com.universidad.gymclass.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object MyReservationsScreen : Screen("my_reservations_screen")
    object ClassDetailScreen : Screen("class_detail_screen/{classId}?reservationId={reservationId}") {
        fun createRoute(classId: String, reservationId: String? = null): String {
            return if (reservationId != null) {
                "class_detail_screen/$classId?reservationId=$reservationId"
            } else {
                "class_detail_screen/$classId"
            }
        }
    }
}