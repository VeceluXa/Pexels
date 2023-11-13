package com.danilovfa.pexels.presentation.screen.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.pexels.domain.lce.lceFlow
import com.danilovfa.pexels.domain.lce.onEachContent
import com.danilovfa.pexels.domain.lce.onEachError
import com.danilovfa.pexels.domain.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private var photosJob: Job? = null

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        getCuratedPhotos()
    }

    private fun getCuratedPhotos() {
        photosJob?.cancel()
        photosJob = lceFlow {
            emit(photoRepository.cacheCuratedPhotos())
        }
            .onStart { _isLoading.emit(true) }
            .onEachError { _isLoading.emit(false) }
            .onEachContent { _isLoading.emit(false) }
            .launchIn(viewModelScope)
    }
}
