package com.xenikii.newsapp.features.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xenikii.newsapp.data.model.SourceItem
import com.xenikii.newsapp.data.repository.NewsRepository

class HomeViewModelFactory(private val sources: List<SourceItem>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                repository = NewsRepository(),
                sources = sources,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
