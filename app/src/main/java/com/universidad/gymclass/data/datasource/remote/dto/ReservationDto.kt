package com.universidad.gymclass.data.datasource.remote.dto

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// This DTO must match the structure of the document in Firestore
data class ReservationDto(
    val userId: String = "",
    val classId: String = "",
    val classDate: Date = Date(),
    val status: String = "CONFIRMED",
    @ServerTimestamp
    val createdAt: Date? = null // Added the missing field
)