package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirestoreDataSource
import com.universidad.gymclass.data.mapper.ReservationMapper
import com.universidad.gymclass.domain.model.Reservation
import com.universidad.gymclass.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource,
    private val mapper: ReservationMapper
) : ReservationRepository {

    override suspend fun createReservation(reservation: Reservation): Result<String> {
        return try {
            val dto = mapper.toDto(reservation)
            val newReservationId = firestoreDataSource.createReservation(dto)
            Result.success(newReservationId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserReservations(userId: String): Flow<List<Reservation>> {
        return firestoreDataSource.getUserReservationsFlow(userId).map { reservationPairs ->
            reservationPairs.map { (id, dto) ->
                mapper.toDomain(dto, id)
            }
        }
    }

    override suspend fun cancelReservation(reservationId: String): Result<Unit> {
        return try {
            firestoreDataSource.cancelReservation(reservationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasExistingReservation(userId: String, classId: String): Boolean {
        return firestoreDataSource.hasExistingReservation(userId, classId)
    }

    override suspend fun getExistingReservation(userId: String, classId: String): Reservation? {
        val result = firestoreDataSource.getExistingReservation(userId, classId)
        return result?.let { (id, dto) ->
            mapper.toDomain(dto, id)
        }
    }

    override suspend fun getReservation(reservationId: String): Reservation? {
        val result = firestoreDataSource.getReservation(reservationId)
        return result?.let { (id, dto) ->
            mapper.toDomain(dto, id)
        }
    }
}