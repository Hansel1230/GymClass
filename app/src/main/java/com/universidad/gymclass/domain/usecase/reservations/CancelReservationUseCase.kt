package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.repository.ReservationRepository
import javax.inject.Inject

class CancelReservationUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    suspend operator fun invoke(reservationId: String): Result<Unit> {
        return repository.cancelReservation(reservationId)
    }
}