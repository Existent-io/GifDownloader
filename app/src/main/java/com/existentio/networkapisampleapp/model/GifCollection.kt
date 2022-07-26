package com.existentio.networkapisampleapp.data

/**
 * Requested gif data collection.
 * @property data contains main information: urls, size, etc.
 * @property meta contains additional request info.
 * @property pagination contains pagination addition info.
 **/
data class GifCollection(
    val data: List<Gif>?,
//    val gif: Gif?,
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
