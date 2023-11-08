package com.danilovfa.pexels.data.remote.model

import com.danilovfa.pexels.domain.model.Photo
import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("src")
    val urls: PhotoSourceResponse,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("photographer")
    val photographer: String
) {
    fun toDomain() = Photo(
        id = id,
        urlOriginal = urls.original,
        urlCompressed = urls.compressed,
        width = width,
        height = height,
        authorName = photographer
    )
}
