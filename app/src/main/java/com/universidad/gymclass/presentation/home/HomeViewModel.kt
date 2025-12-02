package com.universidad.gymclass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.usecase.classes.GetClassesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val classes: List<GymClass> = emptyList(),
    val isLoading: Boolean = true,
    val isUserLoggedOut: Boolean = false,
    val selectedDay: Int? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    getClassesUseCase: GetClassesUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _selectedDay = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<HomeState> = combine(
        getClassesUseCase(),
        _selectedDay
    ) { classes, selectedDay ->
        HomeState(
            classes = classes,
            selectedDay = selectedDay,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState(isLoading = true)
    )

    private val _isUserLoggedOut = MutableStateFlow(false)
    val isUserLoggedOut: StateFlow<Boolean> = _isUserLoggedOut.asStateFlow()


    fun onDaySelected(day: Int?) {
        _selectedDay.value = day
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _isUserLoggedOut.value = true
        }
    }
}