package com.universidad.gymclass.domain.model

// Using Int for dayOfWeek to ensure compatibility with all Android versions.
// 1 = Monday, 7 = Sunday
data class GymClass(
    val id: String,
    val name: String,
    val instructor: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val availableSlots: Int,
    val dayOfWeek: Int
)