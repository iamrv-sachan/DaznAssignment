package com.example.daznassignment.data.di

import com.example.daznassignment.data.repo.NewsRepo
import com.example.daznassignment.data.service.NewsService
import com.example.daznassignment.domain.NewsRepoInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): NewsService =
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)

    @Provides
    fun getRepoImplementation(newsService: NewsService) : NewsRepoInterface{
        return NewsRepo(newsService)
    }

}

