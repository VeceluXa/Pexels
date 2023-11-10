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
    val isBookmarked: Boolean = false
): Parcelable {
    fun toDomain() = Photo(
        id = id,
        urlOriginal = urlOriginal,
        urlCompressed = urlCompressed,
        width = width,
        height = height,
        authorName = authorName,
        isBookmarked = isBookmarked
    )

    companion object {
        fun empty() = PhotoUi(
            id = 0,
            urlOriginal = "",
            urlCompressed = "",
            width = 0,
            height = 0,
            authorName = "",
            isBookmarked = false
        )
    }
}

fun Photo.toUi() = PhotoUi(
    id = id,
    urlOriginal = urlOriginal,
    urlCompressed = urlCompressed,
    width = width,
    height = height,
    authorName = authorName,
    isBookmarked = isBookmarked
)
