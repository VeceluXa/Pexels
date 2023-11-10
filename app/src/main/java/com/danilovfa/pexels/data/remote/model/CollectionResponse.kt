package com.danilovfa.pexels.data.remote.model

import com.google.gson.annotations.SerializedName

data class CollectionResponse(
    @SerializedName("title")
    val title: String
)
