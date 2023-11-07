package com.danilovfa.pexels.ui.composables.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HorizontalLoader(
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = loading) {
        LinearProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}