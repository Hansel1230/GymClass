package com.universidad.gymclass.domain.model

data class AuthResult(
    val user: User? = null,
    val errorMessage: String? = null
)