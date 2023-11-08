package com.danilovfa.pexels.data.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.data.local.PexelsDao
import com.danilovfa.pexels.data.local.model.toEntity
import com.danilovfa.pexels.data.paging.pagingFlow
import com.danilovfa.pexels.data.remote.PexelsApi
import com.danilovfa.pexels.domain.model.Photo
import com.danilovfa.pexels.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val pexelsDao: PexelsDao,
    private val pexelsApi: PexelsApi
) : PhotoRepository {
    override fun getPhotos(query: String): Flow<PagingData<Photo>> =
        pagingFlow { pageNumber, pageSize ->
            withContext(Dispatchers.IO) {
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
            withContext(Dispatchers.IO) {
                pexelsDao.getPhotos(
                    offset = pageNumber * pageSize,
                    pageSize = pageSize
                ).map { it.toDomain() }
            }
        }


    override suspend fun addToFavorites(photo: Photo) = withContext(Dispatchers.IO) {
        pexelsDao.insertPhoto(photo.toEntity())
    }
}