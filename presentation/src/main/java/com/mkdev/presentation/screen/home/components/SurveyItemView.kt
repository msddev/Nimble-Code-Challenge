package com.mkdev.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mkdev.domain.model.survey.SurveyModel
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.GlideImageLoader
import com.mkdev.presentation.common.utils.pagerFadeTransition
import com.mkdev.presentation.theme.CommonColors
import com.mkdev.presentation.theme.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyItemView(
    modifier: Modifier,
    survey: SurveyModel,
    page: Int,
    pagerState: PagerState,
    onSurveyClick: () -> Unit,
) {
    Box {
        GlideImageLoader(
            modifier = modifier.background(CommonColors.ScreenBackColor),
            imageUrl = survey.coverImageUrl,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = modifier.background(color = Color.Black.copy(alpha = 0.6f))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.Padding2xLarge)
                .align(Alignment.BottomCenter)
        ) {

            PagerIndicator(page = page, pagerState = pagerState)

            Text(
                text = survey.title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

            Text(
                text = survey.description,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeightMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                ),
                onClick = onSurveyClick
            ) {
                Text(
                    text = stringResource(R.string.take_this_survey),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun PagerIndicator(page: Int, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val horizontalScrollState = rememberScrollState()
    val localDensity = LocalDensity.current

    val startIndex = maxOf(0, minOf(pagerState.currentPage - 2, pagerState.pageCount - 5))
    val endIndex = minOf(pagerState.pageCount - 1, startIndex + 4)

    Row(
        modifier = Modifier
            .pagerFadeTransition(page = page, pagerState = pagerState)
            .fillMaxWidth()
            .padding(bottom = Dimens.PaddingStandard)
            .horizontalScroll(horizontalScrollState),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (iteration in startIndex..endIndex) {
            val color: Color
            val shape: Shape
            val modifier: Modifier
            if (pagerState.currentPage == iteration) {
                color = Color.DarkGray
                shape = RoundedCornerShape(50)
                modifier = Modifier.size(
                    width = Dimens.PagerIndicatorWidth,
                    height = Dimens.PagerIndicatorHeight
                )
            } else {
                color = Color.LightGray
                shape = CircleShape
                modifier = Modifier.size(Dimens.PagerIndicatorHeight)
            }
            Box(
                modifier = Modifier
                    .padding(Dimens.Padding3xSmall)
                    .clip(shape)
                    .background(color)
                    .then(modifier)
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        coroutineScope.launch {
            val scrollPosition = (pagerState.currentPage - startIndex) *
                    (localDensity.run { Dimens.PagerIndicatorWidth.toPx() } +
                            localDensity.run { Dimens.Padding3xSmall.toPx() } * 2)
            horizontalScrollState.animateScrollTo(scrollPosition.toInt())
        }
    }
}