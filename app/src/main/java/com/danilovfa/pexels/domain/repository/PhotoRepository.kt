package com.danilovfa.pexels.domain.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhotos(query: String): Flow<PagingData<Photo>>
    suspend fun getFeaturedCollections(): List<String>
}
