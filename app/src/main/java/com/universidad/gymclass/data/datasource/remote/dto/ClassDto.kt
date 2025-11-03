package com.universidad.gymclass.data.datasource.remote.dto

// Este DTO debe coincidir con la estructura del documento en Firestore
data class ClassDto(
    val id: String = "",
    val name: String = "",
    val instructor: String = "",
    val description: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val availableSlots: Int = 0,
    val dayOfWeek: String = "" // En Firestore lo guardaremos como String (e.g., "MONDAY")
)