package com.spiridonova.kotlin106

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.*


class LanguageSettingsFragment : PreferenceFragmentCompat() {
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
        setPreferencesFromResource(R.xml.lang_settings, rootKey)
        val locale = PreferenceManager.getDefaultSharedPreferences(activity).getString("locale", "russian")
        val langPref = findPreference<ListPreference>(
                getString(R.string.language_key)
        )
        langPref?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
        val langPrefValue = PreferenceManager.getDefaultSharedPreferences(activity).getString(getString(R.string.language_key), "false")
        val colPref = findPreference<EditTextPreference>(
                getString(R.string.columns_key)
        )
        colPref?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
        val colPrefValue = PreferenceManager.getDefaultSharedPreferences(activity).getString(getString(R.string.columns_key), "1")
        if (locale == "russian") {
            langPref?.summary = "Язык: русский"
            colPref?.summary = "Число строк текста: $colPrefValue"
        } else {
            langPref?.summary = "Language: english"
            colPref?.summary = "Text lines number: $langPrefValue"
        }
    }

    private fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (preference?.key == getString(R.string.language_key)) {
                val loc = newValue.toString()
            if (loc == "russian") {
                preference.summary = "Язык: русский"
            } else {
                preference.summary = "Language: english"
            }
        }
        if (preference?.key == getString(R.string.columns_key)) {
            val columnsNumber = newValue.toString()
            try {
                val cols = columnsNumber.toInt()
                if (cols<0) {
                    Toast.makeText(context, "Should be positive!", Toast.LENGTH_SHORT).show()
                } else {
                    val loc = PreferenceManager.getDefaultSharedPreferences(activity).getString("locale", "english")
                    if (loc == "russian") {
                        preference.summary = "Число колонок: $columnsNumber"
                    } else {
                        preference.summary = "Columns number: $columnsNumber"
                    }
                }
                return cols >= 0
            } catch (nfe: NumberFormatException) {
                Toast.makeText(context, "Should be number!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}