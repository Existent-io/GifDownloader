package com.existentio.networkapisampleapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.existentio.networkapisampleapp.data.GifCollection
import com.existentio.networkapisampleapp.data.GifRepository
import com.existentio.networkapisampleapp.model.GifItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(
    private val gifRepository: GifRepository
) : ViewModel() {

    val gifsTrending = MutableLiveData<GifCollection>()
    val gifsRandom = MutableLiveData<MutableList<GifItem?>>()

    var gifs = mutableListOf<GifItem?>()
    var requestCounter = 20

    val errorMessage = MutableLiveData<String>()

    fun provideGifData() {
        val response = gifRepository.requestGifData()
        response.enqueue(object : Callback<GifCollection> {
            override fun onResponse(call: Call<GifCollection>, response: Response<GifCollection>) {
                Log.d("SUCCESS", response.toString())
                gifsTrending.postValue(response.body())
            }

            override fun onFailure(call: Call<GifCollection>, t: Throwable) {
                Log.d("FAILED", t.toString())
                errorMessage.postValue(t.message)
            }
        })
    }


    fun provideGifDataRandom() {
        val response = gifRepository.requestGifDataRandom()
        response.enqueue(object : Callback<GifItem> {
            override fun onResponse(call: Call<GifItem>, response: Response<GifItem>) {
                Log.d("SUCCESS RANDOM", response.toString())

                if (requestCounter > 0) {
                    gifs.add(response.body())
                    requestCounter--
                    provideGifDataRandom()
                } else {
                    Log.d("tmp size", gifs.size.toString())
                    gifsRandom.postValue(gifs)
                }

            }

            override fun onFailure(call: Call<GifItem>, t: Throwable) {
                Log.d("FAILED RANDOM", t.toString())
                errorMessage.postValue(t.message)
            }

        })
    }


}
