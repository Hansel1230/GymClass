package com.universidad.gymclass.presentation.class_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.usecase.classes.GetClassDetailUseCase
import com.universidad.gymclass.domain.usecase.reservations.CancelReservationUseCase
import com.universidad.gymclass.domain.usecase.reservations.CreateReservationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class ClassDetailState(
    val gymClass: GymClass? = null,
    val isLoading: Boolean = true,
    val isReserved: Boolean = false,
    val isProcessingReservation: Boolean = false, // Nuevo estado
    val error: String? = null,
    val statusMessage: String? = null,
    val reservationCancelled: Boolean = false
)

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val getClassDetailUseCase: GetClassDetailUseCase,
    private val createReservationUseCase: CreateReservationUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassDetailState())
    val uiState: StateFlow<ClassDetailState> = _uiState.asStateFlow()

    private val classId: String = savedStateHandle.get<String>("classId")!!
    private val reservationId: String? = savedStateHandle.get<String>("reservationId")

    init {
        loadClassDetails(classId)
        _uiState.update { it.copy(isReserved = reservationId != null) }
    }

    private fun loadClassDetails(classId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val gymClass = getClassDetailUseCase(classId)
            _uiState.update {
                if (gymClass != null) {
                    it.copy(gymClass = gymClass, isLoading = false)
                } else {
                    it.copy(error = "Class not found", isLoading = false)
                }
            }
        }
    }

    fun onReserveClicked() {
        if (_uiState.value.isProcessingReservation) return // Prevenir clics múltiples
        
        val userId = authRepository.getCurrentUser()?.id
        if (userId == null) {
            _uiState.update { it.copy(statusMessage = "Error: Usuario no autenticado.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingReservation = true) } // Deshabilitar botón
            
            val reservationDate = Date()
            val result = createReservationUseCase(userId, classId, reservationDate)
            
            result.onSuccess {
                _uiState.update { it.copy(statusMessage = "¡Reserva confirmada con éxito!", isProcessingReservation = false, isReserved = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(statusMessage = "Error: ${error.message}", isProcessingReservation = false) }
            }
        }
    }
    
    fun onCancelClicked() {
        if (_uiState.value.isProcessingReservation) return // Prevenir clics múltiples

        if (reservationId == null) {
            _uiState.update { it.copy(statusMessage = "Error: No se encontró el ID de la reserva.") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingReservation = true) } // Deshabilitar botón

            val result = cancelReservationUseCase(reservationId)
            result.onSuccess {
                _uiState.update { it.copy(statusMessage = "Reserva cancelada.", reservationCancelled = true, isProcessingReservation = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(statusMessage = "Error al cancelar: ${error.message}", isProcessingReservation = false) }
            }
        }
    }
}