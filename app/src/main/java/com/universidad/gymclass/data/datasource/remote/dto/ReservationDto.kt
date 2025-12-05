package com.universidad.gymclass.data.datasource.remote.dto

import com.google.firebase.Timestamp

data class ReservationDto(
    val userId: String = "",
    val classId: String = "",
    val classDate: Timestamp? = null,
    val status: String = "",
    val createdAt: Timestamp = Timestamp.now()
)