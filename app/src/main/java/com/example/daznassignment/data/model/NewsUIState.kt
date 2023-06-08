package com.example.daznassignment.data.model

data class NewsUIState(
    val news: News? = null,
    val loading: Boolean = false,
    val error: String? = null
)