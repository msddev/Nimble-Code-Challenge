package com.mkdev.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.GlideImageLoader
import com.mkdev.presentation.model.local.SurveyModel
import com.mkdev.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyItemView(
    modifier: Modifier,
    survey: SurveyModel,
    pageCount: Int,
    currentPage: Int,
) {
    Box(modifier = modifier) {

        GlideImageLoader(
            modifier = Modifier.fillMaxSize(),
            imageUrl = survey.coverImageUrl,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.6f))
        )


        Column(
            modifier = modifier
                .statusBarsPadding()
                .padding(Dimens.PaddingStandard)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.PaddingLarge),
                    shape = RoundedCornerShape(Dimens.CornerRadius2XLarge),
                    elevation = CardDefaults.cardElevation(Dimens.Elevation0dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f), // Card background color
                        contentColor = Color.White  // Card content color,e.g.text
                    ),
                    onClick = {

                    },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.PaddingStandard,
                                vertical = Dimens.HomeProfileContainerPadding
                            ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Monday, JUNE 15",
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(Dimens.Padding2xSmall))

                            Text(
                                text = "Today",
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Image(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.img_place_holder),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pageCount) { iteration ->
                        val color =
                            if (currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}