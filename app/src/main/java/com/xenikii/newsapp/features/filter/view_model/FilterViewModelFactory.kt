package com.xenikii.newsapp.features.filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xenikii.newsapp.data.model.NewsSearchParams
import com.xenikii.newsapp.data.model.SourceItem


class FilterViewModelFactory(
    val initialParams: NewsSearchParams,
    val availableSources: List<SourceItem>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterViewModel(
                initialParams = initialParams,
                availableSources = availableSources
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
