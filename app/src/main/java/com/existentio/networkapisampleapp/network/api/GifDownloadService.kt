package com.existentio.networkapisampleapp.network.api

import com.existentio.networkapisampleapp.data.GifCollection
import com.existentio.networkapisampleapp.model.GifItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GifDownloadService {
    @GET("v1/gifs/trending?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&limit=50&rating=g")
    fun retrieveGifs(): Call<GifCollection>

    @GET("v1/gifs/random?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&tag=&rating=g")
    fun retrieveGifsRandom(): Call<GifItem>

    @GET("v1/gifs/search?api_key=hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2&limit=25&offset=0&rating=g&lang=en")
    fun retrieveGifsByQuery(
        @Query("q") query: String,
        @Query("offset") offset: String
    ): Call<GifCollection>

    companion object {
        val API_KEY_TEST = "hC15BUTFtt50bPo9y1dPl8Lkz2H41qx2"

        private val BASE_URL = "https://api.giphy.com/"

        fun create(): GifDownloadService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GifDownloadService::class.java)
        }
    }

}


