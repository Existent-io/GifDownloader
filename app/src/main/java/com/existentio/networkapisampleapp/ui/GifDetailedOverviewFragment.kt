package com.existentio.networkapisampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.existentio.networkapisampleapp.databinding.FragmentGifDetailedOverviewBinding

class GifDetailedOverviewFragment : Fragment() {

    private var _binding: FragmentGifDetailedOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGifDetailedOverviewBinding
            .inflate(inflater, container, false)
        return binding.root
    }



}