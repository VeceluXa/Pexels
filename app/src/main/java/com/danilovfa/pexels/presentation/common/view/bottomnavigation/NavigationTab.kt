package com.danilovfa.pexels.presentation.common.view.bottomnavigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.danilovfa.pexels.R
import com.danilovfa.pexels.presentation.common.drawable.PexelIcons
import com.danilovfa.pexels.presentation.screen.destinations.BookmarksScreenDestination
import com.danilovfa.pexels.presentation.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

data class NavigationTab(
    val direction: DirectionDestinationSpec,
    val iconSelected: Painter,
    val iconUnselected: Painter,
    val name: String,
)

@Composable
fun navigationTabs(context: Context) = listOf(
    NavigationTab(
        direction = HomeScreenDestination,
        iconSelected = PexelIcons.HomeFilled,
        iconUnselected = PexelIcons.HomeOutline,
        name = context.getString(R.string.tab_home)
    ),
    NavigationTab(
        direction = BookmarksScreenDestination,
        iconSelected = PexelIcons.BookmarkFilled,
        iconUnselected = PexelIcons.BookmarkOutline,
        name = context.getString(R.string.tab_favorites)
    )
)