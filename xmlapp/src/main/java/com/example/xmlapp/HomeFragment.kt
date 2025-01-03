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

        }
        //recyclerview
        //#1
        val albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayMusicRv.adapter = albumRVAdapter

        //#2
        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album:Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment()).apply{
                        arguments = Bundle().apply{
//                            val gson = Gson()
//                            val albumJson = gson.toJson(album)
//                            putString("album", albumJson)
                        }
                    }
                    .commitAllowingStateLoss()
            }
        })


        //viewpager2
        val bannerVPAdapter = BannerVPAdapter(this)
        binding.homeBannerVp.adapter = bannerVPAdapter
        bannerVPAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerVPAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        return binding.root
    }
}