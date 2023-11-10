package com.danilovfa.pexels.presentation.screen.bookmarks

import androidx.compose.runtime.Stable

@Stable
interface BookmarksController {
    fun saveScrollPosition(scrollPosition: Int)
    fun resetBookmarks()
}
