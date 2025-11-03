package com.universidad.gymclass.data.repository

import com.universidad.gymclass.data.datasource.remote.firebase.FirebaseAuthDataSource
import com.universidad.gymclass.data.mapper.AuthMapper
import com.universidad.gymclass.domain.model.AuthResult
import com.universidad.gymclass.domain.model.User
import com.universidad.gymclass.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val mapper: AuthMapper
) : AuthRepository {
    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuthDataSource.login(email, password)
            val user = result.user?.let { mapper.toDomain(it) }
            AuthResult(user = user)
        } catch (e: Exception) {
            AuthResult(errorMessage = e.message)
        }
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuthDataSource.register(email, password)
            val user = result.user?.let { mapper.toDomain(it) }
            AuthResult(user = user)
        } catch (e: Exception) {
            AuthResult(errorMessage = e.message)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): AuthResult {
        return try {
            val result = firebaseAuthDataSource.signInWithGoogle(idToken)
            val user = result.user?.let { mapper.toDomain(it) }
            AuthResult(user = user)
        } catch (e: Exception) {
            AuthResult(errorMessage = e.message)
        }
    }

    override fun getCurrentUser(): User? {
        return firebaseAuthDataSource.getCurrentUser()?.let { mapper.toDomain(it) }
    }

    override suspend fun signOut() {
        firebaseAuthDataSource.signOut()
    }
}