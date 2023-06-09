package com.example.daznassignment.domain

import com.example.daznassignment.base.RequestResult
import com.example.daznassignment.data.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetNewsDataUseCase @Inject constructor(private val newsRepoInterface: NewsRepoInterface) {
    suspend operator fun invoke(searchString: String) =
        getNewsData {
            newsRepoInterface.getnewsData(searchString)
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
