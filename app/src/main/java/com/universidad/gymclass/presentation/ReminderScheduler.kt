package com.universidad.gymclass.presentation

import android.content.Context
import androidx.work.*
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.SettingsRepository
import com.universidad.gymclass.presentation.workers.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) {
    fun scheduleReminder(reservationId: String, gymClass: GymClass, classDate: Date) {
        val workManager = WorkManager.getInstance(context)

        // Get reminder time from settings
        val reminderMinutes = runBlocking { settingsRepository.reminderTimeMinutes.first() }

        // Parse time and set calendar
        val calendar = Calendar.getInstance()
        calendar.time = classDate
        val timeParts = gymClass.startTime.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())

        // Set reminder based on user preference
        calendar.add(Calendar.MINUTE, -reminderMinutes)
        
        val now = Calendar.getInstance()
        
        if (calendar.after(now)) {
            val delay = calendar.timeInMillis - now.timeInMillis

            val data = Data.Builder()
                .putString(ReminderWorker.KEY_CLASS_NAME, gymClass.name)
                .putString(ReminderWorker.KEY_CLASS_TIME, gymClass.startTime)
                .build()

            val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            workManager.enqueueUniqueWork(
                reservationId,
                ExistingWorkPolicy.REPLACE,
                reminderWorkRequest
            )
        }
    }

    fun cancelReminder(reservationId: String) {
        WorkManager.getInstance(context).cancelUniqueWork(reservationId)
    }
}