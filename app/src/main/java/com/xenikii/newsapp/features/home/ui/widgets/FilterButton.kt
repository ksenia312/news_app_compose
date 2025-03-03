package com.xenikii.newsapp.features.home.ui.widgets

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenikii.newsapp.features.filter.ui.FilterBottomSheet
import com.xenikii.newsapp.features.filter.view_model.FilterViewModel
import com.xenikii.newsapp.features.filter.view_model.FilterViewModelFactory
import com.xenikii.newsapp.features.home.view_model.HomeViewModel
import com.xenikii.newsapp.shared.ui.elements.CustomBottomSheet
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterButton(viewModel: HomeViewModel? = null) {
    val showSheet = remember { mutableStateOf(false) }

    IconButton(
        onClick = { showSheet.value = true }, enabled = viewModel != null
    ) {
        Icon(Icons.Filled.Tune, contentDescription = "Filter")
    }

    if (viewModel != null) {
        val key = remember { mutableStateOf(UUID.randomUUID()) }
        val pageOptions by viewModel.pageOptions.collectAsState()

        LaunchedEffect(showSheet.value) {
            if (showSheet.value) {
                key.value = UUID.randomUUID()
            }
        }

        CustomBottomSheet(
            showSheet = showSheet.value,
            onClosed = { showSheet.value = false },
            modifier = Modifier.fillMaxHeight(0.925f),
            content = {
                val filterModel: FilterViewModel = viewModel(
                    key = key.value.toString(),
                    factory = FilterViewModelFactory(
                        pageOptions.searchParams,
                        viewModel.sources
                    )
                )
                FilterBottomSheet(
                    viewModel = filterModel,
                    onCancel = {
                        showSheet.value = false
                    },
                    onSubmit = {
                        viewModel.fetchNewsWithParams(it)
                        showSheet.value = false
                    },
                )
            })
    }
}