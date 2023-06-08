package com.example.daznassignment.data.model.service

import com.example.daznassignment.data.model.News
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY="fdc8e9b04d2e418094b7644a116bc555"
interface NewsService {

    @Headers("x-api-key:$API_KEY")
    @GET("everything")
    suspend fun getDataFromAPI(
        @Query("q") query: String?
    ): News
}