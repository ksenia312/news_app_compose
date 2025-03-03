package com.xenikii.newsapp.shared.extensions

import com.xenikii.newsapp.data.model.SearchScope

fun SearchScope.displayName(): String = when (this) {
    SearchScope.TITLE -> "Title"
    SearchScope.DESCRIPTION -> "Description"
    SearchScope.CONTENT -> "Content"
}