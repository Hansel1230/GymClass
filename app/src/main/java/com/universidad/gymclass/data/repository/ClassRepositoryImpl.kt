package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirestoreDataSource
import com.universidad.gymclass.data.mapper.ClassMapper
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource,
    private val mapper: ClassMapper
) : ClassRepository {

    override fun getClasses(): Flow<List<GymClass>> {
        return firestoreDataSource.getClassesFlow().map { classPairs ->
            classPairs.map { (id, dto) ->
                mapper.toDomain(dto, id)
            }
        }
    }

    override suspend fun getClassById(classId: String): GymClass? {
        return getClasses().first().find { it.id == classId }
    }
    
    override fun getClassByIdFlow(classId: String): Flow<GymClass?> {
        return firestoreDataSource.getClassByIdFlow(classId).map { result ->
            result?.let { (id, dto) ->
                mapper.toDomain(dto, id)
            }
        }
    }

    override suspend fun updateClassSlots(classId: String, change: Int): Result<Unit> {
        return try {
            firestoreDataSource.updateClassSlots(classId, change.toLong())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}