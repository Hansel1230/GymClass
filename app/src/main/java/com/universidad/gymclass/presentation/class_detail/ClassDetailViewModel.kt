package com.universidad.gymclass.presentation.class_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.usecase.classes.GetClassDetailUseCase
import com.universidad.gymclass.domain.usecase.reservations.CancelReservationUseCase
import com.universidad.gymclass.domain.usecase.reservations.CreateReservationUseCase
import com.universidad.gymclass.domain.usecase.reservations.GetExistingReservationUseCase
import com.universidad.gymclass.presentation.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class ClassDetailState(
    val gymClass: GymClass? = null,
    val isLoading: Boolean = true,
    val isReserved: Boolean = false,
    val isProcessingReservation: Boolean = false,
    val error: String? = null,
    val statusMessage: String? = null,
    val reservationCancelled: Boolean = false
)

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val getClassDetailUseCase: GetClassDetailUseCase,
    private val createReservationUseCase: CreateReservationUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase,
    private val getExistingReservationUseCase: GetExistingReservationUseCase,
    private val authRepository: AuthRepository,
    private val reminderScheduler: ReminderScheduler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassDetailState())
    val uiState: StateFlow<ClassDetailState> = _uiState.asStateFlow()

    private val classId: String = savedStateHandle.get<String>("classId")!!
    private var reservationId: String? = savedStateHandle.get<String>("reservationId")
    private val classDate: Date = Date(savedStateHandle.get<Long>("classDate") ?: System.currentTimeMillis())

    init {
        observeClassDetails()
        checkInitialReservationStatus()
    }

    private fun observeClassDetails() {
        _uiState.update { it.copy(isLoading = true) }
        getClassDetailUseCase(classId).onEach { gymClass ->
            _uiState.update {
                if (gymClass != null) {
                    it.copy(gymClass = gymClass, isLoading = false, error = null)
                } else {
                    it.copy(isLoading = false, error = "Class not found")
                }
            }
        }.launchIn(viewModelScope)
    }
    
    private fun checkInitialReservationStatus() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUser()?.id
            if (userId != null) {
                val existingReservation = getExistingReservationUseCase(userId, classId)
                if (existingReservation != null) {
                    reservationId = existingReservation.id
                    _uiState.update { it.copy(isReserved = true) }
                }
            }
        }
    }

    fun onReserveClicked() {
        if (_uiState.value.isProcessingReservation || _uiState.value.isReserved) return 
        
        val userId = authRepository.getCurrentUser()?.id
        val gymClass = _uiState.value.gymClass
        if (userId == null || gymClass == null) {
            _uiState.update { it.copy(statusMessage = "Error: Usuario o clase no encontrados.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingReservation = true) }
            
            val result = createReservationUseCase(userId, classId, classDate)
            
            result.onSuccess { newReservationId ->
                reservationId = newReservationId
                reminderScheduler.scheduleReminder(newReservationId, gymClass, classDate)
                _uiState.update { 
                    it.copy(
                        statusMessage = "¡Reserva confirmada con éxito!", 
                        isProcessingReservation = false, 
                        isReserved = true
                    ) 
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(
                        statusMessage = "Error: ${error.message}", 
                        isProcessingReservation = false
                    ) 
                }
            }
        }
    }
    
    fun onCancelClicked() {
        if (_uiState.value.isProcessingReservation) return

        val currentReservationId = reservationId
        if (currentReservationId == null) {
            _uiState.update { it.copy(statusMessage = "Error: No se encontró el ID de la reserva.") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingReservation = true) }

            val result = cancelReservationUseCase(currentReservationId)
            result.onSuccess {
                reminderScheduler.cancelReminder(currentReservationId)
                reservationId = null
                _uiState.update { 
                    it.copy(
                        statusMessage = "Reserva cancelada.", 
                        reservationCancelled = true, 
                        isProcessingReservation = false,
                        isReserved = false
                    ) 
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(
                        statusMessage = "Error al cancelar: ${error.message}", 
                        isProcessingReservation = false
                    ) 
                }
            }
        }
    }
}