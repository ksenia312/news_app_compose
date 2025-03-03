package com.xenikii.newsapp.features.home.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.features.home.ui.widgets.HomeAppBar
import com.xenikii.newsapp.features.home.ui.widgets.HomeEmptyAppBar
import com.xenikii.newsapp.features.home.view_model.HomeViewModel
import com.xenikii.newsapp.features.home.view_model.HomeViewModelFactory
import com.xenikii.newsapp.features.sources.ui.SourcesFetchWrapper
import com.xenikii.newsapp.features.sources.view_model.SourcesViewState
import com.xenikii.newsapp.shared.ui.effect.IsScrolledLaunchEffect
import com.xenikii.newsapp.shared.ui.elements.CustomScaffold
import com.xenikii.newsapp.shared.ui.elements.ErrorView
import com.xenikii.newsapp.shared.ui.elements.LoadingView
import com.xenikii.newsapp.shared.ui.widgets.UpButton

@Composable
fun HomeScreen() {
    SourcesFetchWrapper(onLoading = {
        HomeLoadingView()
    }, onError = {
        HomeErrorView(it)
    }, onFetched = {
        HomeSuccessView(it)
    })
}


@Composable
private fun HomeLoadingView() {
    CustomScaffold(
        topBar = {
            HomeEmptyAppBar(
                message = "Loading"
            )
        }) {
        LoadingView("Loading sources...")
    }
}

@Composable
private fun HomeErrorView(error: SourcesViewState.Error) {
    CustomScaffold(
        topBar = {
            HomeEmptyAppBar(message = "Failed to load sources")
        }) {
        ErrorView("${error.message}\n${error.details ?: ""}")
    }
}

@Composable
private fun HomeSuccessView(
    sources: List<SourceItem>,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(sources)),
) {
    val listState = rememberLazyListState()
    val showUpButton = remember { mutableStateOf(false) }

    IsScrolledLaunchEffect(listState) { isScrolled ->
        showUpButton.value = isScrolled
    }

    CustomScaffold(
        topBar = { HomeAppBar(viewModel) },
        floatingActionButton = { if (showUpButton.value) UpButton(listState) },
    ) {
        HomeView(
            listState = listState,
            viewModel = viewModel,
        )
    }
}