package com.xenikii.newsapp.features.home.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.shared.ui.effect.BottomLaunchEffect

@Composable
fun NewsList(
    newsItems: List<NewsItem>,
    onLoadMore: (() -> Unit)? = null,
    isLoading: Boolean,
    listState: LazyListState,
    onItemClick: ((NewsItem) -> Unit)
) {
    BottomLaunchEffect(listState) { isAtBottom ->
        if (isAtBottom && !isLoading) {
            onLoadMore?.invoke()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = newsItems.size,
                key = { newsItems[it].id }) { i ->
                val newsItem = newsItems[i]
                NewsItemCard(newsItem) {
                    onItemClick(newsItem)
                }
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
