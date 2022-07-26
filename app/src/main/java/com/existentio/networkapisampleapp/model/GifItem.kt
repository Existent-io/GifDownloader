package com.existentio.networkapisampleapp.model

data class GifItem(
    val data: Gif,
    val meta: Meta?,
    val pagination: Pagination?
)

data class Gif(
    val id: String,
    val title: String?,
    val images: Images
)

data class Images(
    val original: GifOriginalUrl
)

data class GifOriginalUrl(
    val url: String
)

data class Meta(
    val status: Int,
    val message: String,
    val responseId: String
)

data class Pagination(
    val totalCount: Int,
    val count: Int,
    val offset: Int
)
