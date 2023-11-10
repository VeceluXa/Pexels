package com.danilovfa.pexels.presentation.screen.bookmarks

interface BookmarksController {
    fun saveScrollPosition(scrollPosition: Int)
    fun resetBookmarks()
}