package com.xenikii.newsapp.data.model

data class SourcesResponse(val sources: List<SourceItem>)

data class SourceItem(val id: String, val name: String)