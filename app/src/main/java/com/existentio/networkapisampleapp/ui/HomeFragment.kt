package com.existentio.networkapisampleapp.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.existentio.networkapisampleapp.R
import com.existentio.networkapisampleapp.data.Gif as Gif
//import com.existentio.networkapisampleapp.model.Gif as GifItem
import com.existentio.networkapisampleapp.data.GifRepository
import com.existentio.networkapisampleapp.databinding.FragmentHomeBinding
import com.existentio.networkapisampleapp.model.GifItem
import com.existentio.networkapisampleapp.network.api.GifDownloadService

class HomeFragment : Fragment() {

    private var gifsTrending = mutableListOf<Gif>()
    private var gifsRandom = mutableListOf<GifItem?>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val gifDownloadService: GifDownloadService = GifDownloadService.create()

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapterItems: GifAdapterTrendingItems
    private lateinit var adapterRandomItems: GifAdapterRandomItems

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapterItems = GifAdapterTrendingItems()
        adapterRandomItems = GifAdapterRandomItems()
        binding.horizontalRecyclerViewTrending.adapter = adapterItems
        binding.horizontalRecyclerViewRandom.adapter = adapterRandomItems

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = HomeViewModel(GifRepository(gifDownloadService))
//
        loadGifsTrendingItems()
        loadGifsRandomItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadGifsTrendingItems() {
        viewModel.gifsTrending.observe(this, Observer {
            Log.d("observer  ", it.toString())
            Log.d("observer data ", it.data.toString())

            gifsTrending.addAll(it.data!!)

            Log.d("gifsArray size", gifsTrending.size.toString())
            adapterItems.setGifCollection(it.data)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifData()
    }


    private fun loadGifsRandomItems() {
        viewModel.gifsRandom.observe(this, Observer {
//            Log.d("observer data ", it.data.toString())
            gifsRandom.addAll(it)
            Log.d("gifsArrayRandom it", it.toString())
            Log.d("gifsArrayRandom it", gifsRandom.get(0).toString())

            Log.d("gifsArrayRandom size", gifsRandom.size.toString())
//            adapterRandomItems.setGifRandomUrl(it.data.images.original.url)

            adapterRandomItems.setGifs(gifsRandom)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifDataRandom()
    }

    fun provideGifData() = viewModel.provideGifData()

}
