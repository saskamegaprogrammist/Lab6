package com.spiridonova.kotlin106

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.spiridonova.kotlin106.databinding.ActivityMainBinding
import com.spiridonova.kotlin106.settings.NewContextWrapper

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener{
    private lateinit var mainBinding: ActivityMainBinding
    private var textColor: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        checkTheme()
        checkImage()
        setText()
    }

    override fun attachBaseContext(newBase: Context?) {
        val loc = PreferenceManager.getDefaultSharedPreferences(newBase).getString("locale", "ru")
        Log.d("locale", loc.toString())
        super.attachBaseContext(NewContextWrapper.wrap(newBase!!, loc))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        recreate()
    }

    private fun checkTheme() {
        val darkMode = PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean("dark_mode", false)
        Log.d("dark_mode", darkMode.toString())
        when (darkMode) {
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                this.textColor = resources.getColor(R.color.dark)
            }
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                this.textColor = resources.getColor(R.color.white)
            }
        }
        delegate.applyDayNight()
    }
    private fun setText() {
        val columnsString= PreferenceManager.getDefaultSharedPreferences(baseContext).getString("columns_key", "2")
        val columns = columnsString?.toInt();
        Log.d("cols", columnsString.toString())

        for (i in 1..columns!!) {
            var textView = TextView(applicationContext)
            textView.text = getString(R.string.random_string)
            textView.setTextColor(textColor)
            mainBinding.columnsLayout.addView(textView)
        }
    }

    private fun checkImage() {
        val imageMode = PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean("pic_key", false)
        Log.d("pic_key", imageMode.toString())
        when (imageMode) {
            false -> {
                mainBinding.cover.visibility = View.INVISIBLE
            }
            true -> {
                mainBinding.cover.visibility = View.VISIBLE
            }
        }
        delegate.applyDayNight()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this, PreferencesActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}