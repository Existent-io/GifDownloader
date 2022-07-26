package com.existentio.networkapisampleapp.data

import com.existentio.networkapisampleapp.network.api.GifDownloadService

class GifRepository constructor(private val gifDownloadService: GifDownloadService) {

    fun requestGifData() = gifDownloadService.retrieveGifs()

    fun requestGifDataRandom() = gifDownloadService.retrieveGifsRandom()
}

