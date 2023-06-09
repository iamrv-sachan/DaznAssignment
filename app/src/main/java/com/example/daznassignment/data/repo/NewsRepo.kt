package com.example.daznassignment.data.repo

import com.example.daznassignment.domain.NewsRepoInterface
import com.example.daznassignment.data.model.News
import com.example.daznassignment.data.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepo @Inject constructor(private val newsService: NewsService) : NewsRepoInterface {
    override suspend fun getnewsData(search: String): News {
        return withContext(Dispatchers.IO) {
            return@withContext newsService.getDataFromAPI(search)
        }
    }

}