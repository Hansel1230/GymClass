package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.model.Reservation
import com.universidad.gymclass.domain.repository.ReservationRepository
import javax.inject.Inject

class GetExistingReservationUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    suspend operator fun invoke(userId: String, classId: String): Reservation? {
        return repository.getExistingReservation(userId, classId)
    }
}