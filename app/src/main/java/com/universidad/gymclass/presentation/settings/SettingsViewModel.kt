package com.universidad.gymclass.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universidad.gymclass.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val reminderTime = settingsRepository.reminderTimeMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 15)

    fun onReminderTimeChanged(minutes: Int) {
        viewModelScope.launch {
            settingsRepository.setReminderTimeMinutes(minutes)
        }
    }
}