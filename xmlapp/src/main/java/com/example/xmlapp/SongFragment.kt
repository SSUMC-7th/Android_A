package com.example.xmlapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xmlapp.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding : FragmentSongBinding

    private var songData = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songData.apply{
            add(Song("라일락", "아이유 (IU)"))
            add(Song("Flu", "아이유 (IU)"))
        }
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root

    }



    private fun initSong(){

    }

    private fun binding(albumSong: ArrayList<Song>){

    }
}

