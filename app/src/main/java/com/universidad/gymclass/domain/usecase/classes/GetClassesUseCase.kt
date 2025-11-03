package com.universidad.gymclass.domain.usecase.classes

import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClassesUseCase @Inject constructor(
    private val repository: ClassRepository
) {
    operator fun invoke(): Flow<List<GymClass>> {
        return repository.getClasses()
    }
}