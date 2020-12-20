package com.spiridonova.kotlin106

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class PreferencesActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.settings_container, AllSettingsFragment())
                .commit()
        }
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        preference: Preference?
    ): Boolean {
        Log.d("a", "a")

        val fragment: Fragment =
            Fragment.instantiate(this, preference!!.fragment, preference.extras)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_container, fragment)
            .addToBackStack(null)
            .commit()

        return true
    }
}