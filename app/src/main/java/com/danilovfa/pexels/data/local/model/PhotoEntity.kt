package com.danilovfa.pexels.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danilovfa.pexels.domain.model.Photo

@Entity
data class PhotoEntity(
    @PrimaryKey val id: Long,
    val urlOriginal: String,
    val urlCompressed: String,
    val width: Int,
    val height: Int,
    val authorName: String
) {
    fun toDomain() = Photo(
        id = id,
        urlOriginal = urlOriginal,
        urlCompressed = urlCompressed,
        width = width,
        height = height,
        authorName = authorName
    )
}

fun Photo.toEntity() = PhotoEntity(
    id = id,
    urlOriginal = urlOriginal,
    urlCompressed = urlCompressed,
    width = width,
    height = height,
    authorName = authorName
)
