package com.universidad.gymclass.data.repository

import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.DayOfWeek
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor() : ClassRepository {

    override fun getClasses(): Flow<List<GymClass>> {
        // Datos de prueba (mock data)
        val mockClasses = listOf(
            GymClass(
                id = "1",
                name = "Yoga Matutino",
                instructor = "Ana García",
                description = "Sesión de yoga para comenzar el día con energía.",
                startTime = "07:00",
                endTime = "08:00",
                availableSlots = 14,
                dayOfWeek = DayOfWeek.MONDAY
            ),
            GymClass(
                id = "2",
                name = "CrossFit Intensivo",
                instructor = "Carlos Ruiz",
                description = "Entrenamiento funcional de alta intensidad.",
                startTime = "18:00",
                endTime = "19:00",
                availableSlots = 20,
                dayOfWeek = DayOfWeek.MONDAY
            ),
            GymClass(
                id = "3",
                name = "Spinning",
                instructor = "Laura Martínez",
                description = "Ciclismo indoor con música motivadora.",
                startTime = "19:00",
                endTime = "20:00",
                availableSlots = 25,
                dayOfWeek = DayOfWeek.TUESDAY
            ),
            GymClass(
                id = "4",
                name = "Boxeo",
                instructor = "Pedro Ramírez",
                description = "Entrenamiento de boxeo para cardio y técnica.",
                startTime = "20:00",
                endTime = "21:00",
                availableSlots = 18,
                dayOfWeek = DayOfWeek.TUESDAY
            ),
            GymClass(
                id = "5",
                name = "Pilates",
                instructor = "Sofía López",
                description = "Fortalece tu core y mejora tu flexibilidad.",
                startTime = "09:00",
                endTime = "10:00",
                availableSlots = 15,
                dayOfWeek = DayOfWeek.WEDNESDAY
            )
        )
        return flowOf(mockClasses)
    }
}