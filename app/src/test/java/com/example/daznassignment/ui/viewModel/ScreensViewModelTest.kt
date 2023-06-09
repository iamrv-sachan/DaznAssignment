package com.example.daznassignment.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.daznassignment.base.RequestResult
import com.example.daznassignment.domain.GetNewsDataUseCase
import com.example.daznassignment.domain.NewsRepoInterface
import com.example.daznassignment.ui.viewModel.ScreensViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@HiltAndroidTest
internal class ScreensViewModelTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

//    private val testDispatcher = StandardTestDispatcher()

    @Inject
    lateinit var newsRepoInterface : NewsRepoInterface

    @Mock
    lateinit var useCase: GetNewsDataUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
        hiltRule.inject()
    }

    @Test
    fun GetNews() = runBlocking{
        Mockito.`when`(
            useCase.invoke("")
        ).thenReturn(
            flow{
                RequestResult.Success(data = null)
            }
        )

        val sut = ScreensViewModel(useCase)

        val result = sut.newsResponse.value

        Assert.assertEquals(0,result.news?.articles?.size)
    }

    @After
    fun tearDown(){
//        Dispatchers.resetMain()
    }
}