package com.xenikii.newsapp.shared.ui.effect

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun IsScrolledLaunchEffect(listState: LazyListState, onScrolled: (Boolean) -> Unit) {
    val isFirstItemScrolled = remember(listState) {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset != 0
        }
    }
    LaunchedEffect(isFirstItemScrolled.value) {
        onScrolled(isFirstItemScrolled.value)
    }
}