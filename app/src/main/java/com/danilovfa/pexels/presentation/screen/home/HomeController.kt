package com.danilovfa.pexels.presentation.screen.home

import com.danilovfa.pexels.presentation.model.ChipUi
import com.danilovfa.pexels.presentation.model.PhotoUi

interface HomeController {
    fun onPhotoClicked(photo: PhotoUi)
    fun onSearchQueryChanged(query: String)
    fun onSearchResetClicked()
    fun onCollectionClicked(collection: ChipUi)
}