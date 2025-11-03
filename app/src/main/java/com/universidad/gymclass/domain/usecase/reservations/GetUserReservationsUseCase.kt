package com.universidad.gymclass.domain.usecase.reservations

import com.universidad.gymclass.domain.model.ReservationWithClassDetails
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.repository.ClassRepository
import com.universidad.gymclass.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserReservationsUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val classRepository: ClassRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<List<ReservationWithClassDetails>> {
        val userId = authRepository.getCurrentUser()?.id
        return if (userId != null) {
            reservationRepository.getUserReservations(userId).map { reservations ->
                reservations.mapNotNull { reservation ->
                    classRepository.getClassById(reservation.classId)?.let { gymClass ->
                        ReservationWithClassDetails(reservation = reservation, gymClass = gymClass)
                    }
                }
            }
        } else {
            emptyFlow()
        }
    }
}