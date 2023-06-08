package com.example.daznassignment.ui.theme.components

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.example.daznassignment.R
import com.example.daznassignment.ui.theme.viewModel.ScreensViewModel
import com.example.daznassignment.baseFragment.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : BaseComposeFragment() {

    private val viewModel: ScreensViewModel by viewModels()
    var detailsScreenFragment: DetailsScreenFragment? = null
    @Composable
    override fun SetupUI() {
        val toShow = "Cricket"
        LaunchedEffect(key1 = Unit) {
            viewModel.getAllNews(toShow)
        }

        val dataResponse = viewModel.newsResponse.collectAsState().value

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
        } else if( dataResponse?.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colors.background
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                ErrorState(Modifier,dataResponse?.error)
            }
        }else{
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    dataResponse?.news?.articles?.size?.let {
                        items(it) { count ->
                            Image(
                                painter = rememberAsyncImagePainter(model = dataResponse.news.articles[count].urlToImage),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(10.dp)
                                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        val bundle = Bundle()
                                        bundle.putInt(INDEX, count)
                                        bundle.putString(SEARCH, toShow)
                                        detailsScreenFragment =
                                            DetailsScreenFragment.newInstance(bundle)
                                        activity?.supportFragmentManager
                                            ?.beginTransaction()
                                            ?.add(
                                                R.id.fragmentContainer,
                                                detailsScreenFragment!!,
                                                "detailsScreenFragment"
                                            )
                                            ?.addToBackStack("detailsScreenFragment")
                                            ?.commit()
                                    },
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val INDEX = "index"
        const val SEARCH = "search"
        fun newInstance() =
            HomeScreenFragment().apply {
                arguments = Bundle()
            }
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
            Text(text = error, color = Color.White)
        }
    }

}