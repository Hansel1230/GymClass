package com.universidad.gymclass.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.universidad.gymclass.domain.model.User
import javax.inject.Inject

class AuthMapper @Inject constructor() {
    fun toDomain(firebaseUser: FirebaseUser): User {
        return User(
            id = firebaseUser.uid,
            email = firebaseUser.email
        )
    }
}