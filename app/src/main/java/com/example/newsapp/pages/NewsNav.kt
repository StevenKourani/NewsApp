package com.example.newsapp.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.repo.NewsRepository
import com.example.newsapp.viewModel.NewsViewModel
import com.example.newsapp.viewModel.NewsViewModelFactory

@Composable
fun NewsNav() {
    val navController = rememberNavController()
    val newsRepository = NewsRepository()
    val viewModel: NewsViewModel = viewModel(factory = NewsViewModelFactory(newsRepository))

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(navController, viewModel)
        }
        composable("news_detail/{articleTitle}") { backStackEntry ->
            val articleTitle = backStackEntry.arguments?.getString("articleTitle")
            val article = viewModel.getArticleByTitle(articleTitle)
            if (article != null) {
                NewsContent(article = article, navController)
            } else {
                Text("Article not found")
            }
        }
    }
}
