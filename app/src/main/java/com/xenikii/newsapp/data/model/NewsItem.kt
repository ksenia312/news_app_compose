package com.xenikii.newsapp.data.model

import android.icu.text.DateFormat
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<NewsItem> = emptyList(),
)

@Parcelize
data class NewsItem(
    @SerializedName("title")
    val title: String = "No title",
    @SerializedName("description")
    val description: String = "No description",
    @SerializedName("author")
    val author: String = "No author",
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("publishedAt")
    val publishedAt: LocalDateTime?,
    @SerializedName("content")
    val content: String?,
    // Add id to primary constructor
    @OptIn(ExperimentalUuidApi::class)
    val id: String = Uuid.random().toString()
) : Parcelable {
    val formattedDate: String
        get() = publishedAt?.let {
            DateFormat.getDateInstance(
                DateFormat.LONG
            ).format(Date.from(it.atZone(ZoneId.systemDefault()).toInstant()))
        } ?: "No date"
}