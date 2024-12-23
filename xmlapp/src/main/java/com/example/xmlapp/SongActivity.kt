package com.example.xmlapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.xmlapp.databinding.ActivitySongBinding

class SongActivity: AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //초기화 부분
        initCliker()


    }
    private fun initCliker(){
        binding.songPlayerDownIv.setOnClickListener{
            finish()
        }
        binding.songPlayIb.setOnClickListener{
            togglePlay(true)
        }
        binding.songPauseIb.setOnClickListener{
            togglePlay(false)
        }
    }

    private fun togglePlay(
        isPlay : Boolean
    ){
        if(isPlay){
            binding.songPlayIb.visibility = View.GONE
            binding.songPauseIb.visibility = View.VISIBLE
        }else{
            binding.songPlayIb.visibility = View.VISIBLE
            binding.songPauseIb.visibility = View.GONE
        }
    }

}