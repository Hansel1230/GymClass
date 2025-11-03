package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirestoreDataSource
import com.universidad.gymclass.data.mapper.ReservationMapper
import com.universidad.gymclass.domain.model.Reservation
import com.universidad.gymclass.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource,
    private val mapper: ReservationMapper
) : ReservationRepository {

    override suspend fun createReservation(reservation: Reservation): Result<Unit> {
        return try {
            val dto = mapper.toDto(reservation)
            firestoreDataSource.createReservation(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserReservations(userId: String): Flow<List<Reservation>> = flow {
        val reservationPairs = firestoreDataSource.getUserReservations(userId)
        val reservations = reservationPairs.map { (id, dto) ->
            mapper.toDomain(dto, id)
        }
        emit(reservations)
    }

    override suspend fun cancelReservation(reservationId: String): Result<Unit> {
        return try {
            firestoreDataSource.cancelReservation(reservationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasExistingReservation(userId: String, classId: String, date: Date): Boolean {
        return firestoreDataSource.hasExistingReservation(userId, classId, date)
    }
}