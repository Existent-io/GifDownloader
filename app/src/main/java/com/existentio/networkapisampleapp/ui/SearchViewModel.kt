package com.existentio.networkapisampleapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.existentio.networkapisampleapp.data.GifCollection
import com.existentio.networkapisampleapp.data.GifRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel constructor(
    private val gifRepository: GifRepository
) : ViewModel() {
    val gifs = MutableLiveData<GifCollection>()
    val errorMessage = MutableLiveData<String>()

    fun provideGifData(query: String, offset: String) {
        val response = gifRepository.requestGifByQuery(query, offset)
        response.enqueue(object : Callback<GifCollection> {
            override fun onResponse(call: Call<GifCollection>, response: Response<GifCollection>) {
                gifs.postValue(response.body())
            }

            override fun onFailure(call: Call<GifCollection>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}