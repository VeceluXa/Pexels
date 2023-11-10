package com.danilovfa.pexels.presentation.common.view.loader

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerCenterDark
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerCenterLight
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerEndDark
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerEndLight
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerStartDark
import com.danilovfa.pexels.presentation.common.theme.PexelsColors.ShimmerStartLight

const val START_OFFSET = 10f

@Composable
fun getShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
            label = ""
    )

    val colors = if (isSystemInDarkTheme()) {
        listOf(ShimmerStartDark, ShimmerCenterDark, ShimmerEndDark)
    } else {
        listOf(ShimmerStartLight, ShimmerCenterLight, ShimmerEndLight)
    }

    return Brush.linearGradient(
        colors = colors,
        start = Offset(START_OFFSET, START_OFFSET),
        end = Offset(translateAnim, translateAnim)
    )
}
