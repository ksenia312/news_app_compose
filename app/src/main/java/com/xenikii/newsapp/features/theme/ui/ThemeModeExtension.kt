package com.xenikii.newsapp.features.theme.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.xenikii.newsapp.features.theme.view_model.ThemeMode

@Composable
fun ThemeMode.isDark(): Boolean {
    return when (this) {
        ThemeMode.Dark -> true
        ThemeMode.Light -> false
        ThemeMode.System -> isSystemInDarkTheme()
    }
}