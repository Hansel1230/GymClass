package com.universidad.gymclass.presentation.class_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.usecase.classes.GetClassDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClassDetailState(
    val gymClass: GymClass? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val getClassDetailUseCase: GetClassDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassDetailState())
    val uiState: StateFlow<ClassDetailState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("classId")?.let { classId ->
            loadClassDetails(classId)
        }
    }

    private fun loadClassDetails(classId: String) {
        viewModelScope.launch {
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
}