package com.xenikii.newsapp.shared.navigator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xenikii.newsapp.data.model.NewsItem
import com.xenikii.newsapp.features.home.ui.HomeScreen
import com.xenikii.newsapp.features.news_item.ui.NEWS_ITEM_PARAM
import com.xenikii.newsapp.features.news_item.ui.NewsItemScreen
import com.xenikii.newsapp.shared.extensions.get


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object NewsItem : Screen("news-item")
}

@Composable
fun AppNavigator() {
    AppNavigatorProvider {
        val navController = LocalNavHostController.current
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(200)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(200)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(200)
                )
            }
        ) {

            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.NewsItem.route) {
                val item = navController.get<NewsItem>(NEWS_ITEM_PARAM)
                NewsItemScreen(item)
            }

        }
    }
}
