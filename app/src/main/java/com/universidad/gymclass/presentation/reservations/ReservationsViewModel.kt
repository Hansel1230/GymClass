package com.universidad.gymclass.presentation.reservations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.ReservationWithClassDetails
import com.universidad.gymclass.domain.usecase.reservations.GetUserReservationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ReservationsState(
    val reservations: List<ReservationWithClassDetails> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val getUserReservationsUseCase: GetUserReservationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationsState())
    val uiState: StateFlow<ReservationsState> = _uiState.asStateFlow()

    init {
        loadReservations()
    }

    private fun loadReservations() {
        getUserReservationsUseCase().onEach { reservations ->
            _uiState.update {
                it.copy(
                    reservations = reservations,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }
}