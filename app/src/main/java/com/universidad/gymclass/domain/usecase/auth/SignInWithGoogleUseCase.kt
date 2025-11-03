package com.universidad.gymclass.domain.usecase.auth

import com.universidad.gymclass.domain.model.AuthResult
import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String): AuthResult {
        return repository.signInWithGoogle(idToken)
    }
}