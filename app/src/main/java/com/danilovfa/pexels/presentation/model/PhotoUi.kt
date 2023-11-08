package com.danilovfa.pexels.presentation.model

import android.os.Parcelable
import com.danilovfa.pexels.domain.model.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUi(
    val id: Long,
    val urlOriginal: String,
    val urlCompressed: String,
    val width: Int,
    val height: Int,
    val authorName: String,
): Parcelable {
    fun toDomain() = Photo(
        id = id,
        urlOriginal = urlOriginal,
        urlCompressed = urlCompressed,
        width = width,
        height = height,
        authorName = authorName
    )
}

fun Photo.toUi() = PhotoUi(
    id = id,
    urlOriginal = urlOriginal,
    urlCompressed = urlCompressed,
    width = width,
    height = height,
    authorName = authorName
)
