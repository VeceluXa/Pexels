package com.danilovfa.pexels.presentation.screen.home

import androidx.compose.runtime.Stable
import com.danilovfa.pexels.presentation.model.ChipUi

@Stable
interface HomeController {
    fun saveScrollPosition(scrollPosition: Int)
    fun onSearchQueryChanged(query: String)
    fun onSearchResetClicked()
    fun onCollectionClicked(collection: ChipUi)
}
