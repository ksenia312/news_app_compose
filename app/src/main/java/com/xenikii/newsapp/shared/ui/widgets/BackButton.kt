package com.xenikii.newsapp.shared.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.xenikii.newsapp.shared.navigator.LocalNavHostController

@Composable
fun BackButton() {
    val navController = LocalNavHostController.current
    IconButton(onClick = {
        navController.popBackStack()
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}