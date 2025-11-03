package com.universidad.gymclass.domain.model

data class ReservationWithClassDetails(
    val reservation: Reservation,
    val gymClass: GymClass
)