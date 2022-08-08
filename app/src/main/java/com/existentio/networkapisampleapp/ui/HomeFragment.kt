package com.existentio.networkapisampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.existentio.networkapisampleapp.R
import com.existentio.networkapisampleapp.adapter.GifAdapterRandomItems
import com.existentio.networkapisampleapp.adapter.GifAdapterTrendingItems
import com.existentio.networkapisampleapp.data.GifRepository
import com.existentio.networkapisampleapp.databinding.FragmentHomeBinding
import com.existentio.networkapisampleapp.network.api.GifDownloadService
import com.existentio.networkapisampleapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

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

        loadGifsTrendingItems()
        loadGifsRandomItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performSearch()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadGifsTrendingItems() {
        viewModel.gifsTrending.observe(this, Observer {
            adapterItems.setGifCollection(it.data!!)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifData()
    }


    private fun loadGifsRandomItems() {
        viewModel.gifsRandom.observe(this, Observer {
            adapterRandomItems.setGifs(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifDataRandom()
    }

    private fun performSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchApps(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchApps(newText)
                return true
            }
        })
    }

    private fun searchApps(textQuery: String?) {
        if (textQuery!!.isNotEmpty()) {
            val result = "result"
            setFragmentResult("tqKey", bundleOf(result to textQuery))
            val navController = findNavController()
            navController.navigate(R.id.action_HomeFragment_to_SearchFragment)
        }

    }


}
