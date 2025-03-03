package com.xenikii.newsapp.data.model

import java.time.LocalDateTime

data class NewsSearchParams(
    val sortBy: SearchSortBy = SearchSortBy.RELEVANCY,
    val query: String?,
    val sources: List<SourceItem>,
    val scopes: List<SearchScope> = listOf(SearchScope.TITLE),
    val fromDate: LocalDateTime?,
    val toDate: LocalDateTime?,
    val language: String?
) {
    constructor(availableSources: List<SourceItem>) : this(
        sources = if (availableSources.isNotEmpty()) listOf(availableSources.first()) else emptyList(),
        sortBy = SearchSortBy.RELEVANCY,
        scopes = listOf(SearchScope.TITLE),
        query = null,
        fromDate = null,
        toDate = null,
        language = null
    )
}

enum class SearchSortBy(val apiName: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt");
}

enum class SearchScope(val apiName: String) {
    TITLE("title"),
    DESCRIPTION("description"),
    CONTENT("content");
}