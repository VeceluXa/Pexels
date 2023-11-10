package com.danilovfa.pexels.presentation.common.view.photo

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.danilovfa.pexels.presentation.common.view.loader.getShimmerBrush
import com.danilovfa.pexels.presentation.model.PhotoUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.ZoomablePhoto(
    photo: PhotoUi,
    modifier: Modifier = Modifier,
    onError: (String) -> Unit,
    scrollState: ScrollState? = null
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(getSizeModifier(photo))
            .background(
                brush = getShimmerBrush(),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(photo.urlCompressed)
                .scale(Scale.FIT)
                .crossfade(true)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        )

        ZoomableImage(
            painter = painter,
            imageAlign = Alignment.Center,
            contentScale = ContentScale.Crop,
            scrollState = scrollState,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
private fun ColumnScope.getSizeModifier(photo: PhotoUi): Modifier {
    return if (photo.width == 0 || photo.height == 0) {
        Modifier.weight(1f)
    } else {
        val aspectRatio = getAspectRatio(photo.width, photo.height)
        Modifier.aspectRatio(ratio = aspectRatio, matchHeightConstraintsFirst = false)
    }
}