package com.my.booktrackerapp.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.my.booktrackerapp.R
import com.my.booktrackerapp.notification.DailyReminder
import com.my.booktrackerapp.util.NightMode
import android.Manifest
import android.content.pm.PackageManager

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var prefManager: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                DailyReminder().setDailyReminder(requireContext())
                (activity as? SettingsActivity)?.showToast("Reminder: On, set reminder at 09.00am")
            } else {
                findPreference<SwitchPreference>(getString(R.string.pref_key_notify))?.isChecked = false
                (activity as? SettingsActivity)?.showToast("Notifications will not show without permission")
            }
        }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val darkModePref = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        darkModePref?.setOnPreferenceChangeListener { preference, newValue ->
            when (newValue.toString()) {
                getString(R.string.pref_dark_auto) -> updateTheme(NightMode.AUTO.value)
                getString(R.string.pref_dark_on) -> updateTheme(NightMode.ON.value)
                getString(R.string.pref_dark_off) -> updateTheme(NightMode.OFF.value)
            }
            true
        }

        val notificationPref = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notificationPref?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true) {
                checkNotificationPermission()
            } else {
                DailyReminder().cancelAlarm(requireContext())
                (activity as? SettingsActivity)?.showToast("Reminder: Off")
            }
            true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = prefManager.edit()
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (requireContext().checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)) {
                PackageManager.PERMISSION_GRANTED -> {
                    DailyReminder().setDailyReminder(requireContext())
                    (activity as? SettingsActivity)?.showToast("Reminder: On, set reminder at 09.00am")
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}