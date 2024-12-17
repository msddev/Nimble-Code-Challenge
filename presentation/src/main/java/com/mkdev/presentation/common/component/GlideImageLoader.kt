package com.mkdev.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun GlideImageLoader(
    modifier: Modifier,
    imageUrl: String,
    contentScale: ContentScale,
) {
    val color = Color.Black
    val colorPainter = remember { ColorPainter(color) }

    GlideImage(
        modifier = modifier,
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = contentScale,
        ),
        previewPlaceholder = colorPainter,
        failure = {
            Box(modifier = modifier.background(color))
        },
        loading = {
            Box(modifier = modifier.background(color))
        }
    )
}