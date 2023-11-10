package com.danilovfa.pexels.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danilovfa.pexels.data.local.model.PhotoEntity

@Dao
interface PexelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: PhotoEntity)

    @Query("SELECT * FROM photoEntity LIMIT :pageSize OFFSET :offset")
    fun getPhotos(offset: Int, pageSize: Int, ): List<PhotoEntity>

    @Query("DELETE FROM photoEntity WHERE id = :id")
    fun deletePhoto(id: Long)

    @Query("SELECT isBookmarked FROM photoEntity WHERE id = :id")
    fun isBookmarked(id: Long): Boolean
}