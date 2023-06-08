package com.example.daznassignment.domain

import com.example.daznassignment.data.model.News
import com.example.daznassignment.data.model.RequestResult
import com.example.daznassignment.data.model.repo.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetNewsDataUseCase @Inject constructor(private val newsRepo: NewsRepo) {
    suspend operator fun invoke(searchString: String) =
        getNewsData {
            newsRepo.getNews(searchString)
        }
    private suspend fun getNewsData(serviceInvoker: suspend () -> News): Flow<RequestResult<Any>> {
        return flow {
            try {
                val result = serviceInvoker.invoke()
                emit(RequestResult.Success(result))
            } catch (e: Exception) {
                emit(RequestResult.Error(e))
            }
        }
    }
}
