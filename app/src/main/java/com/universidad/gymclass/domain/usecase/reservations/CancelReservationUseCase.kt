package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.repository.ClassRepository
import com.universidad.gymclass.domain.repository.ReservationRepository
import javax.inject.Inject

class CancelReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(reservationId: String): Result<Unit> {
        val reservation = reservationRepository.getReservation(reservationId)
            ?: return Result.failure(Exception("Reserva no encontrada."))

        val slotUpdateResult = classRepository.updateClassSlots(reservation.classId, 1)

        return slotUpdateResult.fold(
            onSuccess = {
                reservationRepository.cancelReservation(reservationId)
            },
            onFailure = {
                Result.failure(Exception("No se pudo actualizar el número de lugares."))
            }
        )
    }
}