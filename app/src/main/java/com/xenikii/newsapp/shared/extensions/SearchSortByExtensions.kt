package com.xenikii.newsapp.shared.extensions

import com.xenikii.newsapp.data.model.SearchSortBy

fun SearchSortBy.displayName(): String = when (this) {
    SearchSortBy.RELEVANCY -> "Relevancy"
    SearchSortBy.POPULARITY -> "Popularity"
    SearchSortBy.PUBLISHED_AT -> "Published at"
}