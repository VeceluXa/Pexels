package com.danilovfa.pexels.presentation.screen.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.danilovfa.pexels.R
import com.danilovfa.pexels.presentation.common.preview.ThemePreviewParameter
import com.danilovfa.pexels.presentation.common.theme.PexelsTheme
import com.danilovfa.pexels.presentation.common.view.loader.HorizontalLoader
import com.danilovfa.pexels.presentation.common.view.photo.PhotosGrid
import com.danilovfa.pexels.presentation.common.view.toolbar.Toolbar
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.presentation.screen.destinations.BookmarksScreenDestination
import com.danilovfa.pexels.presentation.screen.destinations.DetailsScreenDestination
import com.danilovfa.pexels.presentation.screen.destinations.HomeScreenDestination
import com.danilovfa.pexels.presentation.screen.details.DetailsNavArgs
import com.danilovfa.pexels.utils.extension.isLoading
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Destination
fun BookmarksScreen(
    destinationsNavigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<DetailsScreenDestination, Boolean>,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val bookmarks = viewModel.bookmarksFlow.collectAsLazyPagingItems()
    val state by viewModel.stateFlow.collectAsState()

    resultRecipient.onNavResult { doRefreshResult ->
        when (doRefreshResult) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> {
                if (doRefreshResult.value) {
                    viewModel.resetBookmarks()
                    viewModel.getBookmarks()
                }
            }
        }
    }

    BookmarksLayout(
        bookmarks = bookmarks,
        controller = viewModel,
        savedScrollPosition = state.savedScrollPosition,
        onPhotoClick = { photo, position ->
            viewModel.saveScrollPosition(position)
            destinationsNavigator.navigate(
                DetailsScreenDestination(
                    DetailsNavArgs(
                        photo = photo,
                        isFromHomeTab = false
                    )
                )
            )
        },
        onExploreClick = {
            destinationsNavigator.popBackStack()
            destinationsNavigator.navigate(HomeScreenDestination)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookmarksLayout(
    bookmarks: LazyPagingItems<PhotoUi>,
    savedScrollPosition: Int,
    controller: BookmarksController,
    onPhotoClick: (PhotoUi, Int) -> Unit,
    onExploreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Toolbar(title = stringResource(R.string.bookmarks))

        HorizontalLoader(
            loading = bookmarks.isLoading(),
            modifier = Modifier.fillMaxWidth()
        )

        var isRefreshing by remember { mutableStateOf(false) }
        if (bookmarks.isLoading().not()) {
            isRefreshing = false
        }
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = {
                controller.resetBookmarks()
                bookmarks.refresh()
                isRefreshing = true
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .pullRefresh(
                    state = pullRefreshState,
                    enabled = bookmarks
                        .isLoading()
                        .not()
                )
        ) {
            PhotosGrid(
                photos = bookmarks,
                onClick = onPhotoClick,
                onRetryClick = bookmarks::retry,
                onExploreClick = onExploreClick,
                isBookmarksGrid = true,
                savedScrollPosition = savedScrollPosition,
                modifier = Modifier.fillMaxSize()
            )

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
@Preview
private fun Preview(@PreviewParameter(ThemePreviewParameter::class) useDarkTheme: Boolean) {
    val controller = object : BookmarksController {
        override fun saveScrollPosition(scrollPosition: Int) = Unit
        override fun resetBookmarks() = Unit
    }

    val state = BookmarksState()

    val pagingData = PagingData.empty<PhotoUi>(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            append = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false)
        )
    )
    val bookmarks = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    PexelsTheme(darkTheme = useDarkTheme) {
        BookmarksLayout(
            bookmarks = bookmarks,
            savedScrollPosition = state.savedScrollPosition,
            controller = controller,
            onPhotoClick = { _, _ -> },
            onExploreClick = { }
        )
    }
}