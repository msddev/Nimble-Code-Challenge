package com.mkdev.presentation.screen.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.model.mock.fakeSurveyData

@Composable
internal fun HomeScreenContent(
    modifier: Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    //val lazyPagingItems = viewModel.surveys.collectAsLazyPagingItems()

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        SurveyItemView(
            modifier = Modifier
                .fillMaxSize(),
            survey = fakeSurveyData[page],
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
        )
    }
}