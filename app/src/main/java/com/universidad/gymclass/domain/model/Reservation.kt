package com.universidad.gymclass.domain.model

import java.util.Date

data class Reservation(
    val id: String = "",
    val userId: String = "",
    val classId: String = "",
    val classDate: Date,
    val status: String = "CONFIRMED" // e.g., CONFIRMED, CANCELLED
)