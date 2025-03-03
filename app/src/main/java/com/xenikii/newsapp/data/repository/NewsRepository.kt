package com.xenikii.newsapp.data.repository

import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.data.model.NewsSearchParams
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.data.network.*

class NewsRepository {
    private val apiService = ApiClient.apiService

    suspend fun fetchNews(
        pageSize: Int,
        page: Int,
        params: NewsSearchParams
    ): List<NewsItem> {
        val response = apiService.fetchNews(
            pageSize = pageSize,
            page = page,
            sortBy = params.sortBy.apiName,
            query = params.query,
            scopes = params.scopes.joinToString(",") { it.apiName },
            sources = params.sources.joinToString(",") { it.id },
            fromDate = params.fromDate?.toString(),
            toDate = params.toDate?.toString(),
            language = params.language
        )
        println(response.errorBody()?.string())
        println(response.message())
        if (response.isSuccessful) {
            return response.body()?.articles ?: emptyList()
        }
        if (response.code() == 426) {
            throw NewsCompletedException()
        }
        throw Exception("Failed to fetch news")
    }

    suspend fun fetchSources(): List<SourceItem> {
        val response = apiService.fetchSources()
        println(response.errorBody()?.string())
        println(response.message())
        if (response.isSuccessful) {
            return response.body()?.sources ?: emptyList()
        }
        throw Exception("Failed to fetch sources")
    }
}

class NewsCompletedException() : Throwable()