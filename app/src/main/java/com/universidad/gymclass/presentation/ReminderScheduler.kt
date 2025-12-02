package com.universidad.gymclass.presentation

import android.content.Context
import androidx.work.*
import com.universidad.gymclass.domain.model.GymClass
import com.universidad.gymclass.domain.repository.SettingsRepository
import com.universidad.gymclass.presentation.workers.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
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

        // --- Start of Correction ---
        // Use SimpleDateFormat to reliably parse different time formats (e.g., "18:00" or "06:00 PM")
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeFormatAmPm = SimpleDateFormat("hh:mm a", Locale.getDefault())
        
        val timeDate = try {
            timeFormat.parse(gymClass.startTime)
        } catch (e: Exception) {
            try {
                timeFormatAmPm.parse(gymClass.startTime)
            } catch (e: Exception) {
                null // Return null if time format is invalid
            }
        } ?: return // Do not schedule if the time is invalid

        val timeCalendar = Calendar.getInstance().apply { time = timeDate }
        // --- End of Correction ---

        // Set calendar to the correct day and parsed time
        val calendar = Calendar.getInstance()
        calendar.time = classDate
        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

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