package com.danilovfa.pexels.domain.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(): Flow<PagingData<Photo>>
    suspend fun updateBookmark(photo: Photo)
    suspend fun isBookmarked(id: Long): Boolean
}
