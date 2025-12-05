package com.universidad.gymclass.domain.repository

import com.universidad.gymclass.domain.model.AuthResult
import com.universidad.gymclass.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
    suspend fun signInWithGoogle(idToken: String): AuthResult
    fun getCurrentUser(): User?
    suspend fun signOut()
}