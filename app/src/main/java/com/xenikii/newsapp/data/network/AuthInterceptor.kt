package com.xenikii.newsapp.data.network

import com.xenikii.newsapp.data.config.AppConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val updatedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${AppConfig.getInstance().newsApiKey}").build()
        return chain.proceed(updatedRequest)
    }
}

class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        println("Request URL: ${originalRequest.url}")
        return chain.proceed(originalRequest)
    }
}

