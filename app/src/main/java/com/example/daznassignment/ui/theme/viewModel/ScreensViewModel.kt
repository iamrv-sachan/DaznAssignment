package com.example.daznassignment.ui.theme.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daznassignment.data.model.repo.NewsRepo
import com.example.daznassignment.data.model.NewsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScreensViewModel @Inject constructor(private val repository: NewsRepo) : ViewModel() {

    private val newsResponseMLD = MutableLiveData<NewsUIState>()
    val newsResponse: LiveData<NewsUIState>
        get() = newsResponseMLD

    fun getAllNews(toShow: String) {
        viewModelScope.launch {
            newsResponseMLD.value = NewsUIState(loading = true)
            try {
                val response = repository.getNews(toShow)
                newsResponseMLD.value =
                    newsResponseMLD.value?.copy(news = response, loading = false)

            }catch (e: Exception) {
                newsResponseMLD.value =
                    newsResponseMLD.value?.copy(news = null, loading = false, error = true)
            }
        }
    }
}