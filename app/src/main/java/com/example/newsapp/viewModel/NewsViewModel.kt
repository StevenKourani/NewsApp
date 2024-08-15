package com.example.newsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repo.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> get() = _newsList

//    private val _country = MutableLiveData<String>()
//    val country: LiveData<String> get() = _country
//
//    private val _category = MutableLiveData<String>()
//    val category: LiveData<String> get() = _category

    private val _topic = MutableLiveData<String>()
    val topic: LiveData<String> get() = _topic

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

//    fun setString(newCountry: String, newCategory: String) {
//        _country.value = newCountry
//        _category.value = newCategory
//        fetchNews(newCountry, newCategory)
//    }

    fun setString(newTopic: String) {
        _topic.value = newTopic
        fetchNews(newTopic)
    }

    fun getArticleByTitle(title: String?): Article? {
        return newsList.value?.find { it.title == title }
    }


    private fun fetchNews(topic: String) {
        viewModelScope.launch {
            try {
                val response = newsRepository.getTopHeadlines(topic)
                _newsList.value = response.articles
            } catch (e: Exception) {
                _error.value = "Failed to load news"
            }
        }
    }
}
