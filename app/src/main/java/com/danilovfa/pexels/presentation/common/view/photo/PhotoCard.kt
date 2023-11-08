package com.danilovfa.pexels.presentation.common.view.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.danilovfa.pexels.presentation.common.view.loader.getShimmerBrush
import com.danilovfa.pexels.presentation.model.PhotoUi

@Composable
fun PhotoCard(
    photo: PhotoUi,
    onClick: (PhotoUi) -> Unit,
    modifier: Modifier = Modifier,
    doShowName: Boolean = false
) {
    val aspectRatio = getAspectRatio(photo.width, photo.height)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = aspectRatio, matchHeightConstraintsFirst = false)
            .padding(12.dp)
            .background(
                brush = getShimmerBrush(),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick(photo)
            }
    ) {
//        val state = rememberAsyncImagePainter(
//            ImageRequest.Builder(LocalContext.current)
//                .data(photo.urlCompressed)
//                .scale(Scale.FILL)
//                .crossfade(true)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .build()
//        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.urlCompressed)
                .scale(Scale.FILL)
                .crossfade(true)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = photo.authorName,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        if (doShowName) {
            Text(
                text = photo.authorName,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

private fun getAspectRatio(width: Int, height: Int): Float {
    return width.toFloat() / height
}