package com.my.booktrackerapp.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.my.booktrackerapp.R
import com.my.booktrackerapp.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var prefManager: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = prefManager.edit()
    }

    override fun onResume() {
        super.onResume()
        prefManager.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        prefManager.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == getString(R.string.pref_key_dark)) {
            val mode = sharedPreferences.getString(key, getString(R.string.pref_dark_auto))
            updateTheme(NightMode.valueOf(mode!!.uppercase(Locale.US)).value)
            (activity as? SettingsActivity)?.showToast(getString(R.string.pref_dark_title) + " updated")
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}