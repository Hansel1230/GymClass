package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirebaseAuthDataSource
import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) : AuthRepository {
    // Implementación de los métodos de AuthRepository
}