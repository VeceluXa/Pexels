package com.danilovfa.pexels.data.remote.model

import com.google.gson.annotations.SerializedName

data class CollectionsEnvelopeResponse(
    @SerializedName("collections")
    val collections: List<CollectionResponse>
)
