package com.danilovfa.pexels.data.remote.model

import com.google.gson.annotations.SerializedName

data class PhotosEnvelopeResponse(
    @SerializedName("photos")
    val photos: List<PhotoResponse>
)
