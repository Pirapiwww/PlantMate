package com.example.plantmate

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun setLanguage(languageCode: String) {
    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(languageCode)
    )
}

fun isIndonesianLanguage(): Boolean {
    val appLocales = AppCompatDelegate.getApplicationLocales()

    val locale = when {
        !appLocales.isEmpty -> appLocales[0]
        else -> Locale.getDefault()
    }

    return locale.language == "id"
}
