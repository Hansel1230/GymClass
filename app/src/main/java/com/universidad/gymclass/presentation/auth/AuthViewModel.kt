package com.universidad.gymclass.presentation.auth

import androidx.lifecycle.ViewModel
import com.universidad.gymclass.domain.usecase.auth.LoginUseCase
import com.universidad.gymclass.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    // Lógica del ViewModel para la autenticación
}