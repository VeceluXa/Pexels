package com.danilovfa.pexels.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.danilovfa.pexels.R

object PexelIcons {
    val HomeFilled: Painter @Composable get() = painterResource(R.drawable.ic_home_filled)
    val HomeOutline: Painter @Composable get() = painterResource(R.drawable.ic_home_outline)
    val BookmarkFilled: Painter @Composable get() = painterResource(R.drawable.ic_bookmark_filled)
    val BookmarkOutline: Painter @Composable get() = painterResource(R.drawable.ic_bookmark_outline)
    val Search: Painter @Composable get() = painterResource(R.drawable.ic_search)
    val Close: Painter @Composable get() = painterResource(R.drawable.ic_close)
}