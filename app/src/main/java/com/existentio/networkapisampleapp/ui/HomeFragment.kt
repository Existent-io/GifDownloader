package com.existentio.networkapisampleapp.ui

//import com.existentio.networkapisampleapp.model.Gif as GifItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.existentio.networkapisampleapp.data.Gif
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
            gifsTrending.addAll(it.data!!)
            adapterItems.setGifCollection(it.data)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifData()
    }


    private fun loadGifsRandomItems() {
        viewModel.gifsRandom.observe(this, Observer {
            gifsRandom.addAll(it)
            adapterRandomItems.setGifs(gifsRandom)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifDataRandom()
    }

}
