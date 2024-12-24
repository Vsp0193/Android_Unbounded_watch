package com.peak.unbounded.features.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.unbounded.realizingself.R
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    size: Dp ,
    placeholderResId: Int? = null,
    errorResId: Int? = null,
    contentScale: ContentScale = ContentScale.Crop,
    circular: Boolean = false,
    placeholderPainter: Painter? = null,
    errorPainter: Painter? = null
) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            if (circular) {
                transformations(CircleCropTransformation())
            }
            placeholderResId?.let { placeholder(it) }
            errorResId?.let { error(it) }
        }
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        contentScale = contentScale
    )

    // Handle placeholder and error painter if provided
    if (placeholderResId != null && placeholderPainter != null && painter.state is coil.compose.AsyncImagePainter.State.Loading) {
        Image(
            painter = placeholderPainter,
            contentDescription = contentDescription,
            modifier = modifier.size(size),
            contentScale = contentScale
        )
    }
    if (errorResId != null && errorPainter != null && painter.state is coil.compose.AsyncImagePainter.State.Error) {
        Image(
            painter = errorPainter,
            contentDescription = contentDescription,
            modifier = modifier.size(size),
            contentScale = contentScale
        )
    }
}
