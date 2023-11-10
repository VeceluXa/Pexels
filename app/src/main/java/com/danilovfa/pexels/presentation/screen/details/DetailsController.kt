package com.danilovfa.pexels.presentation.screen.details

import android.content.Context
import androidx.compose.runtime.Stable
import com.danilovfa.pexels.presentation.model.PhotoUi

@Stable
interface DetailsController {
    fun setPhoto(photo: PhotoUi, isFromHomeTab: Boolean)
    fun onBookmarkClicked()
    fun onDownloadClicked(context: Context)

    fun onPhotoError(message: String)
}
