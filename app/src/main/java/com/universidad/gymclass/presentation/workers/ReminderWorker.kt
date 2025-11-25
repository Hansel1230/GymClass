package com.universidad.gymclass.presentation.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.universidad.gymclass.MainActivity
import com.universidad.gymclass.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val className = inputData.getString(KEY_CLASS_NAME) ?: return Result.failure()
        val classTime = inputData.getString(KEY_CLASS_TIME) ?: return Result.failure()

        showNotification(className, classTime)
        
        return Result.success()
    }

    private fun showNotification(className: String, classTime: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "CLASS_REMINDERS")
            .setSmallIcon(R.drawable.ic_launcher_logo)
            .setContentTitle("¡Tu clase está por comenzar!")
            .setContentText("Tu clase de $className comienza a las $classTime.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    companion object {
        const val KEY_CLASS_NAME = "KEY_CLASS_NAME"
        const val KEY_CLASS_TIME = "KEY_CLASS_TIME"
    }
}