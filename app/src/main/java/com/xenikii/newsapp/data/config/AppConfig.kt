package com.xenikii.newsapp.data.config

import java.io.File
import java.util.Properties

class AppConfig {
    companion object {
        private var instance: AppConfig? = null

        fun getInstance(): AppConfig {
            return instance ?: synchronized(this) {
                instance ?: buildConfig().also { instance = it }
            }
        }

        private fun buildConfig(): AppConfig {
            val config = AppConfig()
            try {
                config.loadConfig()
            } catch (e: Exception) {
                println("Error loading config: ${e.message}")
            }
            return config
        }
    }

    private fun loadConfig() {
        val properties = Properties()
        val file = File("config.properties")
        if (file.exists()) {
            properties.load(file.inputStream())
            newsApiKey = properties.getProperty("NEWS_API_KEY", "")
            newsBaseUrl = properties.getProperty("NEWS_BASE_URL", "")
        }
    }

    var newsApiKey: String = ""
    var newsBaseUrl: String = ""
}