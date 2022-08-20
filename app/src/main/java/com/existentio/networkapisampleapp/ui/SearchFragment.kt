package com.existentio.networkapisampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.existentio.networkapisampleapp.R
import com.existentio.networkapisampleapp.adapter.GifAdapterAllItems
import com.existentio.networkapisampleapp.data.GifRepository
import com.existentio.networkapisampleapp.databinding.FragmentSearchBinding
import com.existentio.networkapisampleapp.network.api.GifDownloadService
import com.existentio.networkapisampleapp.viewmodel.SearchViewModel


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel

    private val gifDownloadService: GifDownloadService = GifDownloadService.create()

    private lateinit var adapterAllGifItems: GifAdapterAllItems


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = SearchViewModel(GifRepository(gifDownloadService))

        setFragmentResultListener("tqKey") { _, bundle ->
            val result = bundle.getString("result")
            binding.searchViewAllGifs.setQuery(result, true)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding
            .inflate(inflater, container, false)

        adapterAllGifItems = GifAdapterAllItems()
        binding.gridRecyclerViewAllItems.adapter = adapterAllGifItems

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performSearch()
    }

    private fun performSearch() {
        binding.searchViewAllGifs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            loadGifs(textQuery, "0")
        } else {
            val navController = findNavController()
            navController.navigate(R.id.action_SearchFragment_to_HomeFragment)
        }
    }

    private fun loadGifs(textQuery: String, offset: String) {
        viewModel.gifs.observe(this, Observer {
            adapterAllGifItems.setGifCollection(it.data!!)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.provideGifData(textQuery, offset)
    }


}