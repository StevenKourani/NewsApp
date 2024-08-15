package com.example.newsapp.repo

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.api.NewsApiService
import com.example.newsapp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository {

    private val apiService: NewsApiService = RetrofitInstance.apiService

    suspend fun getTopHeadlines(topic: String): NewsResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTopHeadlines(topic = topic)
        }
    }
}
