package com.xenikii.newsapp.features.home.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.xenikii.newsapp.data.model.NewsItem
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItemCard(
    newsItem: NewsItem,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            BuildImage(newsItem = newsItem)
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BuildTitle(newsItem = newsItem)
                Spacer(modifier = Modifier.size(16.dp))
                BuildSubtitle(newsItem = newsItem)
            }
        }
    }
}

@Composable
private fun BuildImage(newsItem: NewsItem) {
    val modifier = Modifier
        .fillMaxSize()
        .aspectRatio(2f)
        .background(color = MaterialTheme.colorScheme.tertiary)
    if (newsItem.imageUrl != null) {
        AsyncImage(
            model = newsItem.imageUrl,
            contentDescription = newsItem.title,
            modifier = modifier,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToScale(
                        0.5f, 0.5f, 0.5f, 1f
                    )
                }),
        )
    } else {
        Box(
            modifier = modifier
        )
    }
}

@Composable
private fun BuildTitle(newsItem: NewsItem) {
    Text(
        text = newsItem.title,
        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BuildSubtitle(newsItem: NewsItem) {
    Text(
        text = newsItem.author,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}