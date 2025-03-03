package com.xenikii.newsapp.shared.extensions

import androidx.navigation.NavController
import com.xenikii.newsapp.shared.navigator.Screen
import kotlin.collections.iterator

fun <T> NavController.push(data: Map<String, T>) {
    navigate(Screen.NewsItem.route)

    for ((key, value) in data) {
        currentBackStackEntry?.savedStateHandle?.set<T>(key, value)
    }
}

fun <T> NavController.get(name: String): T? {
    return currentBackStackEntry?.savedStateHandle?.get<T>(name)
}
