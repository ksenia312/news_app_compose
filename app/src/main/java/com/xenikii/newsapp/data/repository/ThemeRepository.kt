package com.xenikii.newsapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

const val THEME_KEY = "ThemePrefs"
const val DARK_MODE_KEY = "isDarkMode"

class ThemeRepository(context: Context) {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(THEME_KEY, Context.MODE_PRIVATE)

    val isDarkMode: Boolean = sharedPrefs.getBoolean(DARK_MODE_KEY, false)

    fun hasSavedTheme(): Boolean {
        return sharedPrefs.contains(DARK_MODE_KEY)
    }

    fun setDarkMode(isDarkMode: Boolean) {
        sharedPrefs.edit { putBoolean(DARK_MODE_KEY, isDarkMode) }
    }
}
