package com.universidad.gymclass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.usecase.classes.GetClassesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

data class HomeState(
    val classes: List<GymClass> = emptyList(),
    val isLoading: Boolean = true,
    val isUserLoggedOut: Boolean = false,
    val selectedDay: DayOfWeek? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClassesUseCase: GetClassesUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadClasses()
    }

    private fun loadClasses() {
        _uiState.update { it.copy(isLoading = true) }
        getClassesUseCase().onEach { classes ->
            _uiState.update {
                it.copy(
                    classes = classes,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onDaySelected(day: DayOfWeek?) {
        _uiState.update { it.copy(selectedDay = day) }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.update { it.copy(isUserLoggedOut = true) }
        }
    }
}