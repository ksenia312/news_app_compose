package com.xenikii.newsapp.features.theme.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenikii.newsapp.features.theme.view_model.ThemeViewModel
import com.xenikii.newsapp.features.theme.view_model.ThemeViewModelFactory

@Composable
fun ThemeProvider(
    viewModel: ThemeViewModel = viewModel(factory = ThemeViewModelFactory(LocalContext.current)),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalThemeViewModel provides viewModel) {
        content()
    }
}

val LocalThemeViewModel = staticCompositionLocalOf<ThemeViewModel> {
    error("No ThemeViewModel provided")
}