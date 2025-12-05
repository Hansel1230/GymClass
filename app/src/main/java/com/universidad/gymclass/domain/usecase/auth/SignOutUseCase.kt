package com.universidad.gymclass.domain.usecase.auth

import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}