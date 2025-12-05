package com.universidad.gymclass.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.usecase.auth.LoginUseCase
import com.universidad.gymclass.domain.usecase.auth.RegisterUseCase
import com.universidad.gymclass.domain.usecase.auth.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = loginUseCase(email, password)
            _uiState.update {
                if (result.user != null) {
                    it.copy(isLoading = false, isAuthenticated = true, error = null)
                } else {
                    it.copy(isLoading = false, isAuthenticated = false, error = result.errorMessage)
                }
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = registerUseCase(email, password)
            _uiState.update {
                if (result.user != null) {
                    it.copy(isLoading = false, isAuthenticated = true, error = null)
                                } else {
                    it.copy(isLoading = false, isAuthenticated = false, error = result.errorMessage)
                }
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = signInWithGoogleUseCase(idToken)
            _uiState.update {
                if (result.user != null) {
                    it.copy(isLoading = false, isAuthenticated = true, error = null)
                } else {
                    it.copy(isLoading = false, isAuthenticated = false, error = result.errorMessage)
                }
            }
        }
    }
}