package com.xenikii.newsapp.features.sources.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SourcesViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _sources = MutableStateFlow<SourcesViewState>(SourcesViewState.Loading)
    val sources: StateFlow<SourcesViewState> = _sources

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            try {
                val fetchedSources = repository.fetchSources()
                println("fetchedSources: ${fetchedSources.joinToString()}")
                _sources.value = SourcesViewState.Success(fetchedSources)
            } catch (e: Exception) {
                println(e.stackTraceToString())
                _sources.value = SourcesViewState.Error(
                    e.message ?: "Unknown error",
                    details = e.cause?.message
                )
            }
        }
    }
}

sealed class SourcesViewState {
    data object Loading : SourcesViewState()

    data class Success(val sources: List<SourceItem>) : SourcesViewState()

    data class Error(val message: String, val details: String? = null) : SourcesViewState()
}