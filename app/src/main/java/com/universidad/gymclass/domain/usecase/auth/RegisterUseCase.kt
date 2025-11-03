package com.universidad.gymclass.domain.usecase.auth

import com.universidad.gymclass.domain.model.AuthResult
import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        return repository.register(email, password)
    }
}