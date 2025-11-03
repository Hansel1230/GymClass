package com.universidad.gymclass.data.mapper

import com.universidad.gymclass.data.datasource.remote.dto.ClassDto
import com.universidad.gymclass.domain.model.GymClass
import java.time.DayOfWeek
import javax.inject.Inject

class ClassMapper @Inject constructor() {

    fun toDomain(dto: ClassDto): GymClass {
        return GymClass(
            id = dto.id,
            name = dto.name,
            instructor = dto.instructor,
            description = dto.description,
            startTime = dto.startTime,
            endTime = dto.endTime,
            availableSlots = dto.availableSlots,
            dayOfWeek = DayOfWeek.valueOf(dto.dayOfWeek.uppercase()) // "MONDAY" -> DayOfWeek.MONDAY
        )
    }
}