package com.xenikii.newsapp.shared.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.xenikii.newsapp.features.theme.ui.LocalThemeViewModel
import com.xenikii.newsapp.features.theme.ui.isDark

@Composable
fun ToggleThemeButton() {
    val viewModel = LocalThemeViewModel.current
    val state = viewModel.themeMode.collectAsState()
    val isDark = state.value.isDark()
    IconButton(onClick = { viewModel.updateTheme(!isDark) }) {
        Icon(
            if (isDark) Icons.Filled.Nightlight else Icons.Outlined.Nightlight,
            contentDescription = "Toggle Theme",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}