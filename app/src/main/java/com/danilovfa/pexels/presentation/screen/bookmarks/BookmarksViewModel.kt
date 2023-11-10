package com.danilovfa.pexels.presentation.screen.bookmarks

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.danilovfa.pexels.domain.repository.PhotoRepository
import com.danilovfa.pexels.presentation.common.viewmodel.StatefulViewModel
import com.danilovfa.pexels.presentation.model.PhotoUi
import com.danilovfa.pexels.presentation.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : StatefulViewModel<BookmarksState>(BookmarksState()), BookmarksController {
    val bookmarksFlow = MutableStateFlow<PagingData<PhotoUi>>(PagingData.empty())
    private var bookmarksJob: Job? = null

    init {
        getBookmarks()
    }

    override fun saveScrollPosition(scrollPosition: Int) {
        updateState { copy(savedScrollPosition = scrollPosition) }
    }

    override fun resetBookmarks() {
        saveScrollPosition(0)
        bookmarksFlow.value = PagingData.empty(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                append = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false)
            )
        )
    }

    fun getBookmarks() {
        bookmarksJob?.cancel()
        bookmarksJob = photoRepository.getFavoritePhotos()
            .cachedIn(viewModelScope)
            .onEach { delay(LOADING_DELAY) }
            .map { pagingData ->
                pagingData.map { photo ->
                    photo.toUi()
                }
            }
            .onEach(bookmarksFlow::emit)
            .launchIn(viewModelScope)
    }

    companion object {
        const val LOADING_DELAY = 300L
    }
}

data class BookmarksState(
    val savedScrollPosition: Int = 0
)
