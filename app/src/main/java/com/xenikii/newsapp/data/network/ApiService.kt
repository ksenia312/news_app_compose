package com.xenikii.newsapp.data.network

import com.google.gson.GsonBuilder
import com.xenikii.newsapp.data.config.AppConfig
import com.xenikii.newsapp.data.model.NewsResponse
import com.xenikii.newsapp.data.model.SourcesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

interface ApiService {
    @GET("everything")
    suspend fun fetchNews(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String?,
        @Query("q") query: String?,
        @Query("sources") sources: String?,
        @Query("searchIn") scopes: String?,
        @Query("from") fromDate: String?,
        @Query("to") toDate: String?,
        @Query("language") language: String?
    ): Response<NewsResponse>

    @GET("sources")
    suspend fun fetchSources(): Response<SourcesResponse>
}

object ApiClient {
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(LogInterceptor())
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AppConfig.getInstance().newsBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

