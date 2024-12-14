package com.mkdev.presentation.screen.thankYou.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun ThankYouScreenContent(
    modifier: Modifier,
) {
    Column(
        modifier = modifier.padding(Dimens.PaddingStandard),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(Dimens.ThankYouImageSize),
            painter = painterResource(R.drawable.img_thanks),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )

        Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.thanks_for_taking_the_survey),
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
    }
}