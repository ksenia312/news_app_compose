package com.xenikii.newsapp.features.news_item.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.shared.ui.elements.CustomScaffold
import com.xenikii.newsapp.shared.ui.widgets.BackButton

const val NEWS_ITEM_PARAM = "newsItem"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItemScreen(newsItem: NewsItem?) {
    val item = remember { newsItem }
    CustomScaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "All articles", style = MaterialTheme.typography.titleLarge
                )
            }, navigationIcon = {
                BackButton()
            })
        }) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (item != null) {
                NewsItemTitle(item)
                NewsItemDescription(item)
                NewsItemImage(item)
                NewsItemDate(item)
                NewsItemContent(item)
                OpenNewsItemButton(item)

            } else {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "No data")
                }
            }
        }
    }
}


@Composable
private fun NewsItemTitle(entity: NewsItem) {
    Text(
        text = entity.title, style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun NewsItemDescription(entity: NewsItem) {
    Text(
        text = entity.description, style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun NewsItemImage(entity: NewsItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.tertiary,
    ) {
        val modifier = Modifier
            .heightIn(max = 200.dp, min = 200.dp)
            .fillMaxWidth()

        if (entity.imageUrl != null) {
            AsyncImage(
                model = entity.imageUrl,
                contentDescription = "Article image",
                modifier = modifier,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(modifier)
        }
    }
}

@Composable
private fun NewsItemDate(entity: NewsItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = entity.formattedDate, style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun NewsItemContent(entity: NewsItem) {
    Text(
        text = entity.content ?: "No data", style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun OpenNewsItemButton(entity: NewsItem) {
    val handler = LocalUriHandler.current
    val url = entity.url

    if (url != null && url.isNotEmpty()) {
        Button(
            onClick = {
                handler.openUri(url)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("View full article")
        }
    }
}