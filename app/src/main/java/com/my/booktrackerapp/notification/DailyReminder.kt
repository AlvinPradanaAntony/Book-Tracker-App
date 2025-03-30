package com.my.booktrackerapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.BookRepository
import java.util.concurrent.Executors

class DailyReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Executors.newSingleThreadExecutor().execute {
            val repository = BookRepository.getInstance(context)
            val totalOfCurrentlyReadBooks =
                repository.getTotalOfCurrentlyReadBooks()

            showNotification(context, totalOfCurrentlyReadBooks)
        }
    }

    fun setDailyReminder(context: Context) {

    }

    fun cancelAlarm(context: Context) {

    }

    private fun showNotification(context: Context, numberOfBooks: Int) {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(context)
        val shouldNotify =
            prefManager.getBoolean(context.getString(R.string.pref_key_notify), false)

    }
}