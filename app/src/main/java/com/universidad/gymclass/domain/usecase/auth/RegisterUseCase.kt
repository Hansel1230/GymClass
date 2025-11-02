package com.universidad.gymclass.domain.usecase.auth

import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    // Lógica para el caso de uso de registro
}