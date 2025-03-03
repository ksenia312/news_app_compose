package com.xenikii.newsapp.features.filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenikii.newsapp.data.model.NewsSearchParams
import com.xenikii.newsapp.data.model.SearchScope
import com.xenikii.newsapp.data.model.SearchSortBy
import com.xenikii.newsapp.data.model.SourceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FilterViewModel(val initialParams: NewsSearchParams, val availableSources: List<SourceItem>) :
    ViewModel() {
    private var _currentParams = MutableStateFlow<NewsSearchParams>(initialParams)
    val currentParams: StateFlow<NewsSearchParams> = _currentParams

    val hasChanges: StateFlow<Boolean> =
        currentParams.map { current -> current != initialParams }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = currentParams != initialParams
        )

    fun hasScope(scope: SearchScope): Boolean {
        return _currentParams.value.scopes.contains(scope)
    }

    fun hasSource(item: SourceItem): Boolean {
        return _currentParams.value.sources.contains(item)
    }

    fun setSort(sortBy: SearchSortBy) {
        _currentParams.value = _currentParams.value.copy(sortBy = sortBy)
    }

    fun toggleScope(scope: SearchScope) {
        val scopes = _currentParams.value.scopes.toMutableList()
        if (hasScope(scope)) {
            scopes.remove(scope)
        } else {
            scopes.add(scope)
        }
        _currentParams.value = _currentParams.value.copy(scopes = scopes)
    }

    fun toggleSource(source: SourceItem) {
        val sources = _currentParams.value.sources.toMutableList()
        if (hasSource(source)) {
            sources.remove(source)
        } else {
            sources.add(source)
        }
        _currentParams.value = _currentParams.value.copy(sources = sources)
    }

    fun validate(): String? {
        var error: String? = null

        if (_currentParams.value.sources.isEmpty()) {
            error = "News sources can\'t be empty"
        }
        if (_currentParams.value.scopes.isEmpty()) {
            error = "${if (error != null) "$error\n" else ""}Search scopes can\'t be empty"
        }
        return error
    }

    fun reset() {
        _currentParams.value = NewsSearchParams(
            availableSources = availableSources,
        )
    }
}