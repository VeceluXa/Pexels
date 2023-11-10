package com.danilovfa.pexels.presentation.common.view.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalLoader(
    loading: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 4.dp
) {
    AnimatedVisibility(visible = loading) {
        LinearProgressIndicator(
            modifier = modifier.height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}