package com.example.daznassignment.data.model.repo

import com.example.daznassignment.data.model.News
import com.example.daznassignment.data.model.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepo @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews(toShow: String): News {
        return withContext(Dispatchers.IO) {
            return@withContext newsService.getDataFromAPI(toShow)
        }
    }

}