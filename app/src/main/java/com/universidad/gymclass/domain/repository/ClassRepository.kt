package com.universidad.gymclass.domain.repository

import com.universidad.gymclass.domain.model.GymClass
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    fun getClasses(): Flow<List<GymClass>>
    suspend fun getClassById(classId: String): GymClass?
    fun getClassByIdFlow(classId: String): Flow<GymClass?>
    suspend fun updateClassSlots(classId: String, change: Int): Result<Unit>
}