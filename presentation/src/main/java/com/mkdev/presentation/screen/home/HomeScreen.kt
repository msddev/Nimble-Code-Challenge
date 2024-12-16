package com.mkdev.presentation.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.mkdev.presentation.screen.home.components.HomeScreenContent
import com.mkdev.presentation.viewmodel.HomeViewModel

@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToThankYou: () -> Unit,
) {
    val surveysPaging = homeViewModel.surveysPaging.collectAsLazyPagingItems()

    HomeScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        surveysPaging = surveysPaging,
        onSurveyClick = onNavigateToThankYou
    )

    BackHandler {}
}