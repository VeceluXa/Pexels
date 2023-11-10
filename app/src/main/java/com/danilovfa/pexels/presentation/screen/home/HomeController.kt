package com.danilovfa.pexels.presentation.screen.home

import com.danilovfa.pexels.presentation.model.ChipUi

interface HomeController {
    fun saveScrollPosition(scrollPosition: Int)
    fun onSearchQueryChanged(query: String)
    fun onSearchResetClicked()
    fun onCollectionClicked(collection: ChipUi)
}