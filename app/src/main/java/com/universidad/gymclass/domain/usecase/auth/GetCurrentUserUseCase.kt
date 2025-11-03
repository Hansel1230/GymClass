package com.universidad.gymclass.domain.usecase.auth

import com.universidad.gymclass.domain.model.User
import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}