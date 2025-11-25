package com.universidad.gymclass.domain.repository

import com.universidad.gymclass.domain.model.Reservation
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun createReservation(reservation: Reservation): Result<String>
    fun getUserReservations(userId: String): Flow<List<Reservation>>
    suspend fun cancelReservation(reservationId: String): Result<Unit>
    suspend fun hasExistingReservation(userId: String, classId: String): Boolean
    suspend fun getExistingReservation(userId: String, classId: String): Reservation?
    suspend fun getReservation(reservationId: String): Reservation?
}
