package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.model.Reservation
import com.universidad.gymclass.domain.repository.ClassRepository
import com.universidad.gymclass.domain.repository.ReservationRepository
import java.util.Date
import javax.inject.Inject

class CreateReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(userId: String, classId: String, date: Date): Result<String> {
        val alreadyExists = reservationRepository.hasExistingReservation(userId, classId)
        if (alreadyExists) {
            return Result.failure(Exception("Ya tienes una reserva para esta clase."))
        }

        val reservation = Reservation(
            userId = userId,
            classId = classId,
            classDate = date,
            status = "CONFIRMED"
        )
        
        val creationResult = reservationRepository.createReservation(reservation)
        
        return creationResult.fold(
            onSuccess = { newReservationId ->
                val slotUpdateResult = classRepository.updateClassSlots(classId, -1)
                if (slotUpdateResult.isSuccess) {
                    Result.success(newReservationId)
                } else {
                    // Rollback: delete the reservation if slot update fails
                    reservationRepository.cancelReservation(newReservationId)
                    Result.failure(Exception("No se pudo actualizar el número de lugares."))
                }
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}