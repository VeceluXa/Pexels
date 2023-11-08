package com.danilovfa.pexels.data.remote.model

import com.google.gson.annotations.SerializedName

data class PhotoSourceResponse(
    @SerializedName("original")
    val original: String,
    @SerializedName("large2x")
    val compressed: String,
)
