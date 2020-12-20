package com.spiridonova.kotlin106.settings

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import java.util.*


class NewContextWrapper(base: Context?) : ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, localeString: String?): ContextWrapper {
            val locale = mapLocales(localeString)
            var context = context
            val config =
                context.resources.configuration
            config.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale)
            } else {
                setSystemLocaleLegacy(config, locale)
            }
            context = context.createConfigurationContext(config)
            return NewContextWrapper(context)
        }

        private fun mapLocales(locale: String?) : Locale {
            when (locale) {
                "russian" -> {
                   return Locale("ru")
                }
                "english" -> {
                   return Locale("en_US")
                }
            }
            return Locale("en_US")
        }


        private fun setSystemLocaleLegacy(
            config: Configuration,
            locale: Locale?
        ) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun setSystemLocale(
            config: Configuration,
            locale: Locale?
        ) {
            config.setLocale(locale)
        }
    }
}