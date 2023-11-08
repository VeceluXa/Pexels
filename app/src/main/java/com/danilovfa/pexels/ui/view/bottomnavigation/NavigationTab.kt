package com.danilovfa.pexels.ui.view.bottomnavigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.danilovfa.pexels.R
import com.danilovfa.pexels.features.destinations.FavoriteScreenDestination
import com.danilovfa.pexels.features.destinations.HomeScreenDestination
import com.danilovfa.pexels.ui.drawable.PexelIcons
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
        direction = FavoriteScreenDestination,
        iconSelected = PexelIcons.BookmarkFilled,
        iconUnselected = PexelIcons.BookmarkOutline,
        name = context.getString(R.string.tab_favorites)
    )
)