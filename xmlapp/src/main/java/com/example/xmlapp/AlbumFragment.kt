package com.example.xmlapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xmlapp.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding :FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)


        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAdapter= AlbumVPAdapter(this)
        binding.albumVp.adapter= albumAdapter
        TabLayoutMediator(binding.albumTl, binding.albumVp){
            tab, position->
            tab.text = information[position]

        }.attach()
        return binding.root
    }
}