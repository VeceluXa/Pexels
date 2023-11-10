package com.danilovfa.pexels.data.repository

import androidx.paging.PagingData
import com.danilovfa.pexels.data.local.PexelsDao
import com.danilovfa.pexels.data.local.model.toEntity
import com.danilovfa.pexels.data.paging.pagingFlow
import com.danilovfa.pexels.domain.model.Photo
import com.danilovfa.pexels.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BookmarkRepositoryImpl(
    private val pexelsDao: PexelsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {
    override fun getBookmarks(): Flow<PagingData<Photo>> =
        pagingFlow { pageNumber, pageSize ->
            withContext(ioDispatcher) {
                pexelsDao.getPhotos(
                    offset = pageNumber * pageSize,
                    pageSize = pageSize
                ).map { it.toDomain().copy(isBookmarked = true) }
            }
        }

    override suspend fun updateBookmark(photo: Photo) = withContext(ioDispatcher) {
        if (photo.isBookmarked) {
            pexelsDao.insertPhoto(photo.toEntity())
        } else {
            pexelsDao.deletePhoto(photo.id)
        }
    }

    override suspend fun isBookmarked(id: Long): Boolean = withContext(ioDispatcher) {
        return@withContext pexelsDao.isBookmarked(id)
    }
}
