package com.example.daznassignment.ui.components

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.example.daznassignment.ui.viewModel.ScreensViewModel
import com.example.daznassignment.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsScreenFragment : BaseComposeFragment() {

    private val viewModel: ScreensViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun SetupUI() {
        LaunchedEffect(key1 = Unit) {
            getSearchString()?.let { viewModel.getAllNews(it) }
        }
        val dataResponse = viewModel.newsResponse.collectAsState().value
        val scope = rememberCoroutineScope()
        val pagerState = getIndex()?.let { rememberPagerState(initialPage = it) }
        if (dataResponse?.loading == true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colors.background
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                LoadingState()
            }
        } else if (dataResponse?.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colors.background
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                ErrorState(Modifier,dataResponse?.error )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
                    .background(Color.DarkGray.copy(alpha = 0.4f))
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    pagerState?.currentPage?.let {
                        Text(
                            text =  dataResponse?.news?.articles?.get(it)?.title.toString(),
                            modifier = Modifier.wrapContentSize(),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text =  dataResponse?.news?.articles?.get(it)?.description.toString(),
                            modifier = Modifier.wrapContentSize(),
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text =  dataResponse?.news?.articles?.get(it)?.author.toString(),
                            modifier = Modifier.wrapContentSize(),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }


                dataResponse?.news?.articles?.size?.let {
                    if (pagerState != null) {
                        HorizontalPager(
                            pageCount = it,
                            state = pagerState,
                            pageSize = PageSize.Fill
                        ) { index ->
                            if(index != it) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = dataResponse.news.articles[index].urlToImage
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .offset(y = -(16).dp)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colors.background)
                        .padding(1.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState?.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(100))
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Go back",
                            modifier = Modifier
                                .size(45.dp)
                                .background(Color.DarkGray, shape = RoundedCornerShape(100))
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                if(pagerState?.currentPage != dataResponse.news?.articles?.size) {
                                    pagerState?.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(100))
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Go forward",
                            modifier = Modifier
                                .size(45.dp)
                                .background(Color.DarkGray, shape = RoundedCornerShape(100))
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val INDEX = "index"
        const val SEARCH = "search"
        fun newInstance(bundle: Bundle) =
            DetailsScreenFragment().apply {
                arguments = bundle
            }
    }

    private fun getIndex(): Int? {
        var index: Int? = null
        arguments?.let {
            index = it.getInt(INDEX)
        }
        return index
    }

    private fun getSearchString(): String? {
        var search: String? = null
        arguments?.let {
            search = it.getString(SEARCH)
        }
        return search
    }

    @Composable
    fun LoadingState(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .align(Alignment.Center),
                color = Color.White,
                strokeWidth = 2.dp
            )
        }
    }

    @Composable
    fun ErrorState(modifier: Modifier = Modifier, error: String) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = error,color = Color.White)
        }
    }
}