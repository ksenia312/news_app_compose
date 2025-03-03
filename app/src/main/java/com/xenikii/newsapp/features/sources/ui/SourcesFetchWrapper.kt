package com.xenikii.newsapp.features.sources.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.features.sources.view_model.SourcesViewModel
import com.xenikii.newsapp.features.sources.view_model.SourcesViewModelFactory
import com.xenikii.newsapp.features.sources.view_model.SourcesViewState

@Composable
fun SourcesFetchWrapper(
    onFetched: @Composable (List<SourceItem>) -> Unit,
    onLoading: @Composable () -> Unit,
    onError: @Composable (SourcesViewState.Error) -> Unit,
    viewModel: SourcesViewModel = viewModel(factory = SourcesViewModelFactory()),
) {
    val state by viewModel.sources.collectAsState()
    when (state) {
        is SourcesViewState.Loading -> onLoading()
        is SourcesViewState.Error -> onError(state as SourcesViewState.Error)
        is SourcesViewState.Success -> onFetched((state as SourcesViewState.Success).sources)
    }
}
