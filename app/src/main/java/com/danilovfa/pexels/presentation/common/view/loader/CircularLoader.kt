package com.danilovfa.pexels.presentation.common.view.loader

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danilovfa.pexels.presentation.common.animation.IconAnimatedVisibility

@Composable
fun CircularLoader(
    loading: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    IconAnimatedVisibility(visible = loading) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 3.dp,
            modifier = modifier
        )
    }
}