package com.universidad.gymclass.data.mapper

import com.universidad.gymclass.data.datasource.remote.dto.ReservationDto
import com.universidad.gymclass.domain.model.Reservation
import javax.inject.Inject

class ReservationMapper @Inject constructor() {

    fun toDomain(dto: ReservationDto, id: String): Reservation {
        return Reservation(
            id = id,
            userId = dto.userId,
            classId = dto.classId,
            classDate = dto.classDate!!.toDate(),
            status = dto.status
        )
    }

    fun toDto(domain: Reservation): ReservationDto {
        return ReservationDto(
            userId = domain.userId,
            classId = domain.classId,
            classDate = com.google.firebase.Timestamp(domain.classDate),
            status = domain.status
        )
    }
}