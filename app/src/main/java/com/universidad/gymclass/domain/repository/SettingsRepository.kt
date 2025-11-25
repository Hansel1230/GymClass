package com.universidad.gymclass.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val reminderTimeMinutes: Flow<Int>
    suspend fun setReminderTimeMinutes(minutes: Int)
}
