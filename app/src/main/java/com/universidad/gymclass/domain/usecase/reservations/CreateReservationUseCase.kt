package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.model.Reservation
import com.universidad.gymclass.domain.repository.ReservationRepository
import java.util.Date
import javax.inject.Inject

class CreateReservationUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    suspend operator fun invoke(userId: String, classId: String, date: Date): Result<Unit> {
        // Validación: verificar si ya existe una reserva.
        val alreadyExists = repository.hasExistingReservation(userId, classId, date)
        if (alreadyExists) {
            return Result.failure(Exception("Ya tienes una reserva para esta clase."))
        }

        val reservation = Reservation(
            userId = userId,
            classId = classId,
            classDate = date,
            status = "CONFIRMED"
        )
        return repository.createReservation(reservation)
    }
}