@file:Suppress("DEPRECATION")

package com.xenikii.newsapp.shared.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.xenikii.newsapp.features.theme.ui.LocalThemeViewModel
import com.xenikii.newsapp.features.theme.ui.ThemeProvider
import com.xenikii.newsapp.features.theme.ui.isDark

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2196F3),
    secondary = Color(0xFF40C4FF),
    tertiary = Color(0xff444446),
    surface = Color(0xFF444446),
    onSurface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF42A5F5),
    secondary = Color(0xFF42A5F5),
    tertiary = Color(0xff0d4b80),
    background = Color.White,
    surface = Color(0xFFF1F8FE),
    onPrimary = Color.White,
    onSecondary = Color.White,

    error = Color(0xFFB00020),

    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

    surfaceTint = Color.Transparent,
    surfaceDim = Color.Transparent,
)

@Composable
fun NewsAppTheme(
    content: @Composable () -> Unit
) {
    ThemeProvider {
        val mode by LocalThemeViewModel.current.themeMode.collectAsState()
        val isDark = mode.isDark()
        val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

        TransparentSystemBars(isDark)
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}

@Composable
fun TransparentSystemBars(isDark: Boolean) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, !isDark) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDark
        )

        onDispose {}
    }
}