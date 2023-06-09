package com.example.daznassignment.domain

import com.example.daznassignment.data.model.News

interface NewsRepoInterface {
    suspend fun getnewsData(search: String): News
}