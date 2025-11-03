package com.universidad.gymclass.domain.repository

import com.universidad.gymclass.domain.model.GymClass
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    fun getClasses(): Flow<List<GymClass>>
    suspend fun getClassById(id: String): GymClass?
}