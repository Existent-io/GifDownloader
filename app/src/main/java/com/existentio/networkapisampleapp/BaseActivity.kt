package com.existentio.networkapisampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.existentio.networkapisampleapp.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}