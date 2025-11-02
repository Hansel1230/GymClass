package com.universidad.gymclass.domain.model

import java.time.DayOfWeek

data class GymClass(
    val id: String,
    val name: String,
    val instructor: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val availableSlots: Int,
    val dayOfWeek: DayOfWeek
)