package com.danilovfa.pexels.domain.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhotos(query: String): Flow<PagingData<Photo>>
    fun getFavoritePhotos(): Flow<PagingData<Photo>>
    suspend fun updateBookmark(photo: Photo)
    suspend fun getFeaturedCollections(): List<String>
    suspend fun isBookmarked(id: Long): Boolean
}
