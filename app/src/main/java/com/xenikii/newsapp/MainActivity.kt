package com.xenikii.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.xenikii.newsapp.shared.navigator.AppNavigator
import com.xenikii.newsapp.shared.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                AppNavigator()
            }
        }
    }
}