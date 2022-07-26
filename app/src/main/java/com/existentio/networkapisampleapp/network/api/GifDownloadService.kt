package com.existentio.networkapisampleapp.network.api

import com.existentio.networkapisampleapp.data.GifCollection
import com.existentio.networkapisampleapp.model.GifItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GifDownloadService {
//    @GET("v1/gifs/search?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&q=drama&limit=100&offset=0&rating=g&lang=en")
    @GET("v1/gifs/trending?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&limit=50&rating=g")
    fun retrieveGifs(): Call<GifCollection>

    @GET("v1/gifs/random?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&tag=&rating=g")
    fun retrieveGifsRandom(): Call<GifItem>

    companion object {
        private val API_KEY_TEST = "hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2"
//        private val API_KEY_PROD = ""

        private val BASE_URL = "https://api.giphy.com/"
        private val PAGE_LIMIT = "100"
        private val RATING = "g"
        private val OFFSET = "0"
        private val LANGUAGE = "en"
        private val SEARCH_ENDPOINT = "v1/gifs/search?/"
        var USER_INPUT = "drama"
        var QUERY_URL = SEARCH_ENDPOINT + "api_key=$API_KEY_TEST" + "&q=$USER_INPUT" +
                "&limit=$PAGE_LIMIT" + "&offset=$OFFSET" + "&rating=$RATING" + "&lang=$LANGUAGE/"

        fun create(): GifDownloadService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GifDownloadService::class.java)
        }
    }

}


