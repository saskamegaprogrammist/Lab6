package com.spiridonova.kotlin106

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.preference.*


class ThemeSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = super.onCreateView(inflater, container, savedInstanceState)
        val darkMode = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("dark_mode", false)
        if (darkMode) {
            view?.setBackgroundColor(resources.getColor(R.color.dark))
        } else {
            view?.setBackgroundColor(resources.getColor(R.color.white))
        }
        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.theme_settings, rootKey)
        val locale = PreferenceManager.getDefaultSharedPreferences(activity).getString("locale", "russian")
        val themePref = findPreference<CheckBoxPreference>(
                getString(R.string.theme_key)
        )
        themePref?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
        val themePrefValue = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(getString(R.string.theme_key), false)
        val picPref = findPreference<SwitchPreference>(
                getString(R.string.pic_key)
        )
        picPref?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
        val picPrefValue = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(getString(R.string.pic_key), false)
        if (locale == "russian") {
            themePref?.summary = "Темная тема включена: $themePrefValue"
            picPref?.summary = "Картинка показывается: $picPrefValue"
        } else {
            themePref?.summary = "Dark mode enabled: $themePrefValue"
            picPref?.summary = "Pic enabled: $themePrefValue"
        }
    }

    private fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val locale = PreferenceManager.getDefaultSharedPreferences(activity).getString("locale", "russian")
        if (preference?.key == getString(R.string.theme_key)) {
            Log.d("lang", newValue.toString())
            if (locale == "russian") {
                preference.summary = "Темная тема включена: " + newValue.toString()
            } else {
                preference.summary = "Dark mode enabled: " + newValue.toString()
            }
        }
        if (preference?.key == getString(R.string.pic_key)) {
            if (locale == "russian") {
                preference.summary = "Картинка показывается: " + newValue.toString()
            } else {
                preference.summary = "Pic enabled: " + newValue.toString()
            }
        }
        return true
    }
}