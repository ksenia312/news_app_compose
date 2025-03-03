package com.xenikii.newsapp.features.home.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.features.home.ui.widgets.NewsList
import com.xenikii.newsapp.features.home.view_model.HomeViewModel
import com.xenikii.newsapp.features.home.view_model.HomeViewState
import com.xenikii.newsapp.features.news_item.ui.NEWS_ITEM_PARAM
import com.xenikii.newsapp.shared.navigator.LocalNavHostController
import com.xenikii.newsapp.shared.extensions.push
import com.xenikii.newsapp.shared.ui.elements.ErrorView
import com.xenikii.newsapp.shared.ui.elements.LoadingView

@Composable
fun HomeView(
    listState: LazyListState,
    viewModel: HomeViewModel,
) {
    val state by viewModel.news.collectAsState()
    val navController = LocalNavHostController.current
    val onItemClick: (NewsItem) -> Unit = {
        navController.push<NewsItem>(mapOf(NEWS_ITEM_PARAM to it))
    }

    LaunchedEffect(state) {
        if (state is HomeViewState.Loading) {
            listState.scrollToItem(0)
        }
    }

    when (state) {
        is HomeViewState.Loading -> LoadingView(text = "Loading news...")
        is HomeViewState.Error -> ErrorView((state as HomeViewState.Error).message)
        is HomeViewState.Success -> NewsList(
            newsItems = (state as HomeViewState.Success).news, isLoading = false,
            onLoadMore = { viewModel.fetchMoreNews() },
            listState = listState,
            onItemClick = onItemClick,
        )

        is HomeViewState.LoadingMore -> NewsList(
            newsItems = (state as HomeViewState.LoadingMore).lastNews,
            listState = listState,
            isLoading = true,
            onItemClick = onItemClick,
        )
    }
}

