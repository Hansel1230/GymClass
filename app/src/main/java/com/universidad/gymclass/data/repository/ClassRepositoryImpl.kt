package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirestoreDataSource
import com.universidad.gymclass.data.mapper.ClassMapper
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource,
    private val mapper: ClassMapper
) : ClassRepository {

    override fun getClasses(): Flow<List<GymClass>> = flow {
        val classDtos = firestoreDataSource.getClasses()
        val gymClasses = classDtos.map { mapper.toDomain(it) }
        emit(gymClasses)
    }

    override suspend fun getClassById(id: String): GymClass? {
        // Por ahora, obtenemos todas y filtramos.
        // En una app real, aquí haríamos una consulta directa a Firestore por ID.
        val gymClasses = firestoreDataSource.getClasses().map { mapper.toDomain(it) }
        return gymClasses.find { it.id == id }
    }
}