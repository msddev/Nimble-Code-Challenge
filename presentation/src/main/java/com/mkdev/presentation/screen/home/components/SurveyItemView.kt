package com.mkdev.presentation.screen.home.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mkdev.domain.entity.survey.SurveyModel
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.GlideImageLoader
import com.mkdev.presentation.common.utils.pagerFadeTransition
import com.mkdev.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyItemView(
    modifier: Modifier,
    survey: SurveyModel,
    page: Int,
    pagerState: PagerState,
) {
    Box {
        GlideImageLoader(
            modifier = modifier,
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

            Row(
                modifier = Modifier
                    .pagerFadeTransition(page = page, pagerState = pagerState)
                    .fillMaxWidth()
                    .padding(bottom = Dimens.PaddingStandard),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { iteration ->
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
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.take_this_survey),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}