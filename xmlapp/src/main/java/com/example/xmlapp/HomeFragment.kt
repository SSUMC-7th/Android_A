package com.example.xmlapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumData = ArrayList<Album>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodayAlbumIv.setOnClickListener{
//            (context as MainActivity).supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_frm, AlbumFragment())
//                .commitAllowingStateLoss()
//        }
        albumData.apply{
            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp6))
            add(Album("Lilac", "아이유", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파", R.drawable.img_album_supernova))
            add(Album("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
        }

        val albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayMusicRv.adapter = albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment())
                    .commitAllowingStateLoss()
            }
        })

        return binding.root
    }
}