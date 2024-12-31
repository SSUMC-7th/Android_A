package com.example.xmlapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment{
        return when(position){
            0-> SongFragment()
            else -> DetailFragment()
        }
    }
    
}