package com.example.daznassignment.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daznassignment.data.model.News
import com.example.daznassignment.data.model.NewsUIState
import com.example.daznassignment.data.model.RequestResult
import com.example.daznassignment.data.model.repo.NewsRepo
import com.example.daznassignment.domain.GetNewsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScreensViewModel @Inject constructor(
    private val getNewsDataUseCase: GetNewsDataUseCase,
) : ViewModel() {

    private val _newsResponse: MutableStateFlow<NewsUIState> =
        MutableStateFlow(NewsUIState())
    val newsResponse: StateFlow<NewsUIState> = _newsResponse
    fun getAllNews(toShow: String) {
        viewModelScope.launch {
            _newsResponse.value = NewsUIState(news = null, loading = true, error = null)
            getNewsDataUseCase(toShow).collect() {
                when (it) {
                    is RequestResult.Success -> {
                        val response = it.data
                        _newsResponse.value = _newsResponse.value.copy(
                            news = response as News,
                            loading = false
                        )
                    }
                    is RequestResult.Error -> {
                        _newsResponse.value =
                            _newsResponse.value.copy(
                                news = null,
                                loading = false,
                                error = it.throwable.message
                            )
                    }
                    else -> {}
                }
            }
        }
    }
}