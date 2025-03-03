package com.xenikii.newsapp.features.home.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xenikii.newsapp.features.home.view_model.HomeViewModel
import com.xenikii.newsapp.shared.ui.widgets.ToggleThemeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEmptyAppBar(
    message: String
) {
    TopAppBar(
        title = { Text(message) },
        actions = {
            FilterButton()
            ToggleThemeButton()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    viewModel: HomeViewModel
) {
    TopAppBar(
        title = {
            Box(modifier = Modifier.padding(4.dp, 8.dp)) {
                HomeSearchField(viewModel)
            }
        },
        actions = {
            FilterButton(viewModel)
            ToggleThemeButton()
        }
    )
}