package com.danilovfa.pexels.presentation.screen.details

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.danilovfa.pexels.domain.lce.LceState
import com.danilovfa.pexels.domain.lce.lceFlow
import com.danilovfa.pexels.domain.lce.onEachContent
import com.danilovfa.pexels.domain.repository.BookmarkRepository
import com.danilovfa.pexels.presentation.common.viewmodel.StatefulViewModel
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.utils.extension.save
import com.danilovfa.pexels.utils.extension.sharePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : StatefulViewModel<DetailsState>(DetailsState()), DetailsController {
    private var downloadJob: Job? = null
    private var bookmarkJob: Job? = null

    override fun setPhoto(photo: PhotoUi, isFromHomeTab: Boolean) {
        updateState { copy(photo = photo) }

        if (isFromHomeTab) {
            checkIfBookmarked()
        } else {
            updateState { copy(bookmarkState = LceState.Content) }
        }
    }

    private fun checkIfBookmarked() {
        bookmarkJob?.cancel()
        bookmarkJob = lceFlow {
            emit(bookmarkRepository.isBookmarked(state.photo.id))
        }
            .onEach { updateState { copy(bookmarkState = it) } }
            .onEachContent { isBookmarked ->
                updateState { copy(photo = photo.copy(isBookmarked = isBookmarked)) }
            }
            .launchIn(viewModelScope)
    }

    override fun onBookmarkClicked() {
        val newPhoto = state.photo.copy(isBookmarked = state.photo.isBookmarked.not())
        bookmarkJob?.cancel()
        bookmarkJob = lceFlow {
            emit(bookmarkRepository.updateBookmark(newPhoto.toDomain()))
        }
            .onEach { updateState { copy(bookmarkState = it) } }
            .onEachContent {
                updateState { copy(photo = newPhoto, shouldRefreshBookmarks = true) }
            }
            .launchIn(viewModelScope)
    }

    override fun onDownloadClicked(context: Context) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(state.photo.urlOriginal)
            .build()

        downloadJob?.cancel()
        downloadJob = lceFlow {
            emit(loader.execute(request))
        }
            .onEach { updateState { copy(downloadState = it) } }
            .onEachContent { imageResult ->
                imageResult.drawable?.let { drawable ->
                    val uri = drawable.toBitmap().save(context)
                    context.sharePhoto(uri)
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onPhotoError(message: String) {
        updateState { copy(isPhotoError = true) }
    }
}

data class DetailsState(
    val photo: PhotoUi = PhotoUi.empty(),
    val isPhotoError: Boolean = false,
    val shouldRefreshBookmarks: Boolean = false,
    val photoBitmap: Bitmap? = null,
    val photoState: LceState = LceState.Loading,
    val bookmarkState: LceState = LceState.Loading,
    val downloadState: LceState = LceState.Content
) {
    val isDownloading get() = downloadState.isLoading
    val isBookmarkLoading get() = bookmarkState.isLoading
}
