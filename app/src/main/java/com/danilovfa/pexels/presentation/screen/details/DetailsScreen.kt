package com.danilovfa.pexels.presentation.screen.details

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danilovfa.pexels.R
import com.danilovfa.pexels.presentation.common.drawable.PexelIcons
import com.danilovfa.pexels.presentation.common.preview.ThemePreviewParameter
import com.danilovfa.pexels.presentation.common.theme.PexelsTheme
import com.danilovfa.pexels.presentation.common.view.button.IconButton
import com.danilovfa.pexels.presentation.common.view.button.LargeButton
import com.danilovfa.pexels.presentation.common.view.button.TextButton
import com.danilovfa.pexels.presentation.common.view.photo.ZoomablePhoto
import com.danilovfa.pexels.presentation.common.view.toolbar.Toolbar
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.presentation.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Composable
@Destination(navArgsDelegate = DetailsNavArgs::class)
fun DetailsScreen(
    destinationsNavigator: DestinationsNavigator,
    refreshResultBackNavigator: ResultBackNavigator<Boolean>,
    detailsNavArgs: DetailsNavArgs,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(detailsNavArgs) {
        viewModel.setPhoto(
            photo = detailsNavArgs.photo,
            isFromHomeTab = detailsNavArgs.isFromHomeTab
        )
    }

    val state by viewModel.stateFlow.collectAsState()

    BackHandler {
        if (detailsNavArgs.isFromHomeTab) {
            destinationsNavigator.navigateUp()
        } else {
            refreshResultBackNavigator.navigateBack(result = state.shouldRefreshBookmarks)
        }
    }

    DetailsLayout(
        state = state,
        onBackClick = {
            if (detailsNavArgs.isFromHomeTab) {
                destinationsNavigator.navigateUp()
            } else {
                refreshResultBackNavigator.navigateBack(result = state.shouldRefreshBookmarks)
            }
        },
        onExploreClick = {
            destinationsNavigator.navigate(HomeScreenDestination) {
                popUpTo(route = HomeScreenDestination.route)
            }
        },
        controller = viewModel
    )
}

@Composable
private fun DetailsLayout(
    state: DetailsState,
    onBackClick: () -> Unit,
    onExploreClick: () -> Unit,
    controller: DetailsController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Toolbar(
            title = state.photo.authorName,
            navigationIcon = PexelIcons.Back,
            onNavigationIconClick = onBackClick
        )

        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            ZoomablePhoto(
                photo = state.photo,
                scrollState = scrollState,
                onError = controller::onPhotoError,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp)
            )

            PhotoButtons(
                state = state,
                controller = controller
            )

            if (state.isPhotoError) {
                ErrorStub(onExploreClick = onExploreClick)
            }
        }
    }
}

@Composable
private fun ErrorStub(
    onExploreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.details_error))
        Spacer(Modifier.height(12.dp))
        TextButton(text = stringResource(R.string.explore), onClick = onExploreClick)
    }
}

@Composable
private fun BookmarkButton(isBookmarked: Boolean, isLoading: Boolean, onClick: () -> Unit) {
    val bookmarkIcon = if (isBookmarked) {
        PexelIcons.BookmarkFilled
    } else {
        PexelIcons.BookmarkOutline
    }

    val tint = if (isBookmarked) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

    IconButton(
        icon = bookmarkIcon,
        loading = isLoading,
        onClick = onClick,
        tint = tint
    )
}

@Composable
private fun PhotoButtons(
    state: DetailsState,
    controller: DetailsController
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LargeButton(
            title = stringResource(R.string.download),
            leadingIcon = PexelIcons.Download,
            loading = state.isDownloading,
            onClick = { controller.onDownloadClicked(context) }
        )

        BookmarkButton(
            isBookmarked = state.photo.isBookmarked,
            isLoading = state.isBookmarkLoading,
            onClick = controller::onBookmarkClicked
        )
    }
}

@Preview
@Composable
private fun Preview(@PreviewParameter(ThemePreviewParameter::class) useDarkTheme: Boolean) {
    val controller = object : DetailsController {
        override fun setPhoto(photo: PhotoUi, isFromHomeTab: Boolean) = Unit
        override fun onBookmarkClicked() = Unit
        override fun onDownloadClicked(context: Context) = Unit
        override fun onPhotoError(message: String) = Unit
    }
    val state = DetailsState(photo = PhotoUi.empty().copy(authorName = "Fyodor Danilov"))

    PexelsTheme(darkTheme = useDarkTheme) {
        DetailsLayout(
            state = state,
            onBackClick = { },
            onExploreClick = { },
            controller = controller
        )
    }
}
