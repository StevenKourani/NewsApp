package com.example.newsapp.api

import com.example.newsapp.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.newsapp.BuildConfig



interface NewsApiService {
    @GET("everything")
    suspend fun getTopHeadlines(
        @Query("q") topic: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): NewsResponse
}

//@GET("top-headlines")
//suspend fun getTopHeadlines(
//    @Query("category") category: String,
//    @Query("country") country: String,
//    @Query("apiKey") apiKey: String
//): NewsResponse