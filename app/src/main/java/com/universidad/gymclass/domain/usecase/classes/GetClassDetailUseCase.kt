package com.universidad.gymclass.domain.usecase.classes

import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClassDetailUseCase @Inject constructor(
    private val repository: ClassRepository
) {
    operator fun invoke(classId: String): Flow<GymClass?> {
        return repository.getClassByIdFlow(classId)
    }
}