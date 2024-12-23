package com.example.xmlapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xmlapp.databinding.ActivitySongBinding

class SongActivity: AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songPlayerDownIv.setOnClickListener {

        }

    }

}