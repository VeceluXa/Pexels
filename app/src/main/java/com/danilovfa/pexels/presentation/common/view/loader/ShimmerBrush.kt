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
import androidx.compose.ui.graphics.Color

@Composable
fun getShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val colors = if (isSystemInDarkTheme()) {
        listOf(Color(0xFF161616), Color(0xFF2A2A2A), Color(0xFF0C0C0C))
    } else {
        listOf(Color(0xFFF2F2F2), Color(0xFFF8F8F8), Color(0xFFEBEBEB))
    }

    return Brush.linearGradient(
        colors = colors,
        start = Offset(10F, 10F),
        end = Offset(translateAnim, translateAnim)
    )
}