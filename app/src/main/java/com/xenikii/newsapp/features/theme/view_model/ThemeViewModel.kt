package com.xenikii.newsapp.features.theme.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.xenikii.newsapp.data.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ThemeMode { Light, Dark, System }

class ThemeViewModel(context: Context) : ViewModel() {
    private val repository = ThemeRepository(context)
    private val initialValue = {
        if (!repository.hasSavedTheme()) ThemeMode.System
        else if (repository.isDarkMode) ThemeMode.Dark
        else ThemeMode.Light

    }.invoke()

    private var _themeMode: MutableStateFlow<ThemeMode> = MutableStateFlow(initialValue)

    val themeMode: StateFlow<ThemeMode> = _themeMode

    fun updateTheme(isDarkMode: Boolean) {
        _themeMode.value = if (isDarkMode) ThemeMode.Dark else ThemeMode.Light
        repository.setDarkMode(isDarkMode)
    }
}