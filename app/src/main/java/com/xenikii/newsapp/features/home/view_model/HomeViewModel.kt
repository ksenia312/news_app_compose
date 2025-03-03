package com.xenikii.newsapp.features.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.data.model.NewsSearchParams
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.data.repository.NewsCompletedException
import com.xenikii.newsapp.data.repository.NewsRepository
import com.xenikii.newsapp.shared.util.Debounce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

const val PAGE_SIZE = 50

class HomeViewModel(val sources: List<SourceItem>, private val repository: NewsRepository) :
    ViewModel() {
    private var _pageOptions =
        MutableStateFlow(
            HomeViewOptions(
                searchParams = NewsSearchParams(availableSources = sources)
            )
        )

    val pageOptions: StateFlow<HomeViewOptions> = _pageOptions

    private val _news = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val news: StateFlow<HomeViewState> = _news

    private val _debounce = Debounce()

    init {
        fetchNews()
    }

    fun fetchMoreNews() {
        if (_pageOptions.value.isCompleted) return
        if (_news.value is HomeViewState.LoadingMore) return

        val currentPage = _pageOptions.value.currentPage
        _pageOptions.value = _pageOptions.value.copy(currentPage = currentPage + 1)

        fetchNews()
    }

    fun fetchByQuery(query: String) {
        val currentParams = _pageOptions.value.searchParams
        if (query.isEmpty()) {
            fetchNewsWithParams(currentParams.copy(query = null))
        } else {
            _debounce.start(duration = 500.milliseconds) {
                fetchNewsWithParams(currentParams.copy(query = query))
            }
        }
    }

    fun fetchNewsWithParams(params: NewsSearchParams) {
        _pageOptions.value = _pageOptions.value.copy(
            currentPage = 1,
            searchParams = params,
            isCompleted = false
        )
        _news.value = HomeViewState.Loading
        fetchNews(force = true)
    }

    private fun fetchNews(force: Boolean = false) {
        if (!force && _pageOptions.value.isCompleted) return
        if (!force && _news.value is HomeViewState.LoadingMore) return

        viewModelScope.launch {
            val lastNews = (_news.value as? HomeViewState.Success)?.news ?: emptyList()
            try {
                _news.value = if (_news.value is HomeViewState.Loading)
                    HomeViewState.Loading
                else
                    HomeViewState.LoadingMore(lastNews)
                val fetchedNews = repository.fetchNews(
                    pageSize = PAGE_SIZE,
                    page = _pageOptions.value.currentPage,
                    params = _pageOptions.value.searchParams
                )
                _news.value = HomeViewState.Success(lastNews + fetchedNews)
            } catch (_: NewsCompletedException) {
                _news.value = HomeViewState.Success(lastNews)
                _pageOptions.value = _pageOptions.value.copy(isCompleted = true)
            } catch (e: Exception) {
                println(e.stackTraceToString())
                _news.value = HomeViewState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

data class HomeViewOptions(
    val currentPage: Int = 1,
    val isCompleted: Boolean = false,
    val searchParams: NewsSearchParams
)

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class LoadingMore(val lastNews: List<NewsItem>) : HomeViewState()
    data class Success(val news: List<NewsItem>) : HomeViewState()
    data class Error(val message: String) : HomeViewState()
}


