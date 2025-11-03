package com.universidad.gymclass.domain.repository

import com.universidad.gymclass.domain.model.Reservation
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ReservationRepository {
    suspend fun createReservation(reservation: Reservation): Result<Unit>
    fun getUserReservations(userId: String): Flow<List<Reservation>>
    suspend fun cancelReservation(reservationId: String): Result<Unit>
    suspend fun hasExistingReservation(userId: String, classId: String, date: Date): Boolean
}