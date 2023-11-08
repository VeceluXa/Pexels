package com.danilovfa.pexels.data.remote

import com.danilovfa.pexels.data.remote.model.CollectionsEnvelopeResponse
import com.danilovfa.pexels.data.remote.model.PhotosEnvelopeResponse
import com.danilovfa.pexels.utils.Constants.DEFAULT_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
    @GET("search")
    suspend fun getPhotosByQuery(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int = DEFAULT_PAGE_SIZE
    ): PhotosEnvelopeResponse

    @GET("curated")
    suspend fun getPopularPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int = DEFAULT_PAGE_SIZE
    ): PhotosEnvelopeResponse

    @GET("collections/featured")
    suspend fun getFeaturedCollections(): CollectionsEnvelopeResponse
}