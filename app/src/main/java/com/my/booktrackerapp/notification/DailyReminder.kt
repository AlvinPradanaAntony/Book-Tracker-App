package com.my.booktrackerapp.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.ui.home.MainActivity
import com.my.booktrackerapp.util.ID_REPEATING
import com.my.booktrackerapp.util.NOTIFICATION_CHANNEL_ID
import com.my.booktrackerapp.util.NOTIFICATION_CHANNEL_NAME
import com.my.booktrackerapp.util.NOTIFICATION_ID
import java.util.Calendar
import java.util.concurrent.Executors

class DailyReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Executors.newSingleThreadExecutor().execute {
            val repository = BookRepository.getInstance(context)
            val totalOfCurrentlyReadBooks = repository.getTotalOfCurrentlyReadBooks()
            showNotification(context, totalOfCurrentlyReadBooks)
        }
    }

    fun setDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

    private fun showNotification(context: Context, numberOfBooks: Int) {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(context)
        val shouldNotify = prefManager.getBoolean(context.getString(R.string.pref_key_notify), false)
        if (shouldNotify) {
            createNotificationChannel(context)

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val title = context.getString(R.string.notification_title, numberOfBooks)
            val subtitle = if (numberOfBooks > 0) {
                context.getString(R.string.notification_subtitle)
            } else {
                context.getString(R.string.notification_subtitle_no_book)
            }
            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(subtitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = context.getString(R.string.notification_subtitle)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}