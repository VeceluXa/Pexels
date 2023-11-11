package com.danilovfa.pexels.presentation.common.view.photo

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.danilovfa.pexels.R
import com.danilovfa.pexels.presentation.common.drawable.PexelIcons
import com.danilovfa.pexels.presentation.common.view.button.TextButton
import com.danilovfa.pexels.presentation.common.view.loader.getShimmerBrush
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.utils.extension.showError
import kotlin.random.Random

@Composable
fun PhotosGrid(
    photos: LazyPagingItems<PhotoUi>,
    onClick: (photo: PhotoUi, savedScrollPosition: Int) -> Unit,
    onRetryClick: () -> Unit,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier,
    savedScrollPosition: Int = 0,
    isBookmarksGrid: Boolean = false
) {
    val listState = rememberLazyStaggeredGridState()
    val context = LocalContext.current

    LaunchedEffect(savedScrollPosition, photos.itemCount) {
        if (photos.itemCount > 0) {
            listState.scrollToItem(savedScrollPosition)
        }
    }

    Box(
        modifier = modifier
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(photos.itemCount) { position ->
                photos[position]?.let { photo ->
                    PhotoCard(
                        photo = photo,
                        onClick = { onClick(photo, listState.firstVisibleItemIndex) },
                        doShowName = isBookmarksGrid
                    )
                }
            }

            item {
                PhotosGridStubs(
                    photos = photos,
                    onRetryClick = onRetryClick,
                    context = context
                )
            }
        }

        PhotosGridFullScreenStubs(
            photos = photos,
            onRetryClick = onRetryClick,
            onExploreClick = onExploreClick,
            isBookmarksGrid = isBookmarksGrid,
            context = context,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun PhotosGridFullScreenStubs(
    photos: LazyPagingItems<PhotoUi>,
    onRetryClick: () -> Unit,
    onExploreClick: () -> Unit,
    isBookmarksGrid: Boolean,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val loadState = photos.loadState
    when {
        loadState.refresh is LoadState.Loading -> {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(12.dp)
            ) {
                items(8) {
                    ShimmerItem()
                }
            }
        }

        loadState.refresh is LoadState.NotLoading && photos.itemCount == 0 -> {
            val textResId = if (isBookmarksGrid) R.string.favorite_empty_stub else R.string.home_empty_stub
            Column(
                modifier = modifier.background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(textResId))
                Spacer(Modifier.height(12.dp))
                TextButton(text = stringResource(R.string.explore), onClick = onExploreClick)
            }
        }

        loadState.refresh is LoadState.Error -> {
            val error = (loadState.refresh as LoadState.Error).error
            context.showError(error)

            Column(
                modifier = modifier.background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = PexelIcons.NoNetwork, contentDescription = null)
                Spacer(Modifier.height(12.dp))
                TextButton(text = stringResource(R.string.error_button), onClick = onRetryClick)
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun PhotosGridStubs(
    photos: LazyPagingItems<PhotoUi>,
    onRetryClick: () -> Unit,
    context: Context = LocalContext.current
) {
    val loadState = photos.loadState
    when (loadState.append) {
        is LoadState.Loading -> {
            repeat(3) {
                ShimmerItem()
            }
        }

        is LoadState.Error -> {
            val error = (loadState.append as LoadState.Error).error
            context.showError(error)

            TextButton(
                text = stringResource(R.string.error_button),
                onClick = onRetryClick,
                modifier = Modifier
                    .padding(vertical = 32.dp)
            )
        }

        else -> {}
    }
}

@Composable
private fun ShimmerItem() {
    val aspectRatio = remember { Random.nextFloat() + .5f }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = aspectRatio, matchHeightConstraintsFirst = false)
            .padding(12.dp)
            .background(
                brush = getShimmerBrush(),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
    )
}
