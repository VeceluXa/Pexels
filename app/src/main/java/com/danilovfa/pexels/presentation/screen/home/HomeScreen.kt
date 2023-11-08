package com.danilovfa.pexels.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.danilovfa.pexels.R
import com.danilovfa.pexels.presentation.common.preview.ThemePreviewParameter
import com.danilovfa.pexels.presentation.common.theme.PexelsTheme
import com.danilovfa.pexels.presentation.common.view.chip.Chip
import com.danilovfa.pexels.presentation.common.view.loader.HorizontalLoader
import com.danilovfa.pexels.presentation.common.view.photo.PhotosGrid
import com.danilovfa.pexels.presentation.common.view.toolbar.Search
import com.danilovfa.pexels.presentation.model.ChipUi
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.presentation.screen.destinations.DetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()
    val photos = viewModel.photosFlow.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        HomeLayout(
            state = state,
            photos = photos,
            controller = viewModel,
            onPhotoClick = { photo ->
                navigator.navigate(DetailsScreenDestination(photo))
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun HomeLayout(
    state: HomeState,
    photos: LazyPagingItems<PhotoUi>,
    controller: HomeController,
    onPhotoClick: (PhotoUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Search(
            value = state.query,
            onValueChange = controller::onSearchQueryChanged,
            onValueResetClick = controller::onSearchResetClicked,
            placeholderText = stringResource(R.string.search),
        )

        ChipsContent(state.collections, controller)

        HorizontalLoader(
            loading = photos.loadState.refresh is LoadState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        PhotosGrid(
            photos = photos,
            onClick = onPhotoClick,
            onRetryClick = {
                photos.retry()
            },
            onExploreClick = controller::onSearchResetClicked,
            doShowName = false
        )
    }
}

@Composable
private fun ChipsContent(collections: List<ChipUi>, controller: HomeController) {
    val listState = rememberLazyListState()

    LaunchedEffect(collections) {
        listState.animateScrollToItem(0)
    }

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp),
        state = listState
    ) {
        items(collections.size) {position ->
            Chip(chip = collections[position], onClick = controller::onCollectionClicked)
            if (position != collections.lastIndex) {
                Spacer(Modifier.width(12.dp))
            }
        }
    }
}

@Composable
@Preview
private fun Preview(@PreviewParameter(ThemePreviewParameter::class) useDarkTheme: Boolean) {
    val controller = object : HomeController {
        override fun onPhotoClicked(photo: PhotoUi) = Unit
        override fun onSearchQueryChanged(query: String) = Unit
        override fun onSearchResetClicked() = Unit
        override fun onCollectionClicked(collection: ChipUi) = Unit
    }

    val state = HomeState()

    val pagingData = PagingData.empty<PhotoUi>(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            append = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false)
        )
    )
    val photos = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    PexelsTheme(darkTheme = useDarkTheme) {
        HomeLayout(state = state, photos = photos, controller = controller, onPhotoClick = {})
    }
}