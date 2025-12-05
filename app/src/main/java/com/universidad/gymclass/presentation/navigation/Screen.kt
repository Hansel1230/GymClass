package com.universidad.gymclass.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object RegisterScreen : Screen("register")
    object HomeScreen : Screen("home")
    object MyReservationsScreen : Screen("my_reservations")
    object SettingsScreen : Screen("settings")
    object ClassDetailScreen : Screen("class_detail/{classId}?reservationId={reservationId}&classDate={classDate}") {
        fun createRoute(classId: String, reservationId: String?, classDate: Long?): String {
            val date = classDate ?: System.currentTimeMillis()
            val resId = reservationId ?: "null"
            return "class_detail/$classId?reservationId=$resId&classDate=$date"
        }
    }
}