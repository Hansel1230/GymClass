package com.universidad.gymclass.domain.usecase.classes

import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.ClassRepository
import javax.inject.Inject

class GetClassDetailUseCase @Inject constructor(
    private val repository: ClassRepository
) {
    suspend operator fun invoke(id: String): GymClass? {
        return repository.getClassById(id)
    }
}