package com.universidad.gymclass.data.mapper

import com.universidad.gymclass.data.datasource.remote.dto.ClassDto
import com.universidad.gymclass.domain.model.GymClass
import javax.inject.Inject

class ClassMapper @Inject constructor() {
    fun toDomain(dto: ClassDto, id: String): GymClass {
        return GymClass(
            id = id,
            name = dto.name,
            instructor = dto.instructor,
            description = dto.description,
            startTime = dto.startTime,
            endTime = dto.endTime,
            availableSlots = dto.availableSlots,
            dayOfWeek = mapDayOfWeekToInt(dto.dayOfWeek) // Re-added conversion logic
        )
    }

    private fun mapDayOfWeekToInt(day: String): Int {
        return when (day.uppercase()) {
            "MONDAY" -> 1
            "TUESDAY" -> 2
            "WEDNESDAY" -> 3
            "THURSDAY" -> 4
            "FRIDAY" -> 5
            "SATURDAY" -> 6
            "SUNDAY" -> 7
            else -> 1 // Default to Monday if data is inconsistent
        }
    }
}