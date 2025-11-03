package com.universidad.gymclass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.usecase.classes.GetClassesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import javax.inject.Inject

data class HomeState(
    val classes: List<GymClass> = emptyList(),
    val selectedDay: DayOfWeek? = null, // null significa "Todos"
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClassesUseCase: GetClassesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadClasses()
    }

    private fun loadClasses() {
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
}