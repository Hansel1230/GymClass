package com.universidad.gymclass.data.datasource.remote.dto

// Este DTO debe coincidir con la estructura del documento en Firestore
data class ClassDto(
    val name: String = "",
    val instructor: String = "",
    val description: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val availableSlots: Int = 0,
    val dayOfWeek: String = "" // Reverted to String to match Firestore data
)