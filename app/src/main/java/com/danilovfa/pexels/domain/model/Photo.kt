package com.danilovfa.pexels.domain.model

data class Photo(
    val id: Long,
    val urlOriginal: String,
    val urlCompressed: String,
    val width: Int,
    val height: Int,
    val authorName: String,
    val isBookmarked: Boolean = false
)