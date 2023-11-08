package com.danilovfa.pexels.data.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.data.local.PexelsDao
import com.danilovfa.pexels.data.local.model.toEntity
import com.danilovfa.pexels.data.paging.pagingFlow
import com.danilovfa.pexels.data.remote.PexelsApi
import com.danilovfa.pexels.domain.model.Photo
import com.danilovfa.pexels.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val pexelsDao: PexelsDao,
    private val pexelsApi: PexelsApi,
    private val ioDispatcher: CoroutineDispatcher
) : PhotoRepository {
    override fun getPhotos(query: String): Flow<PagingData<Photo>> =
        pagingFlow { pageNumber, pageSize ->
            withContext(ioDispatcher) {
                val photosEnvelope = if (query.isEmpty()) {
                    pexelsApi.getPopularPhotos(page = pageNumber, pageSize = pageSize)
                } else {
                    pexelsApi.getPhotosByQuery(query = query, page = pageNumber, pageSize = pageSize)
                }

                photosEnvelope.photos.map { photoResponse ->
                    photoResponse.toDomain()
                }
            }
        }

    override fun getFavoritePhotos(): Flow<PagingData<Photo>> =
        pagingFlow { pageNumber, pageSize ->
            withContext(ioDispatcher) {
                pexelsDao.getPhotos(
                    offset = pageNumber * pageSize,
                    pageSize = pageSize
                ).map { it.toDomain() }
            }
        }


    override suspend fun addToFavorites(photo: Photo) = withContext(ioDispatcher) {
        pexelsDao.insertPhoto(photo.toEntity())
    }

    override suspend fun getFeaturedCollections(): List<String> = withContext(ioDispatcher) {
        return@withContext pexelsApi.getFeaturedCollections().collections.map { it.title }
    }
}