package com.example.daznassignment

import com.example.daznassignment.model.News
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