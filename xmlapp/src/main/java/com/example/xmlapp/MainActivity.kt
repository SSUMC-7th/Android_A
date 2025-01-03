package com.example.xmlapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.xmlapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private var song: Song = Song()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        initClicker()
        inputDummyAlbums()
        inputDummySongs()
        val song = Song(
            binding.mainMiniplayerTitleTv.text.toString(),
            binding.mainMiniplayerSingerTv.text.toString()
        )


    }

    override fun onStart() {
        super.onStart()

        //DB 관련
        val songDB = SongDatabase.getInstance(this)!!
        val spf= getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        song = if(songId==0){
            songDB.songDao().getSong(1)
        }else{
            songDB.songDao().getSong(songId)
        }


    }

    private fun initClicker(){
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = song.second*100

    }
    private fun initBottomNavigation(){
        //homeFragment 초기 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item->
            when (item.itemId){
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm,LockerFragment() )
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun inputDummyAlbums(){
        val albums = listOf(
            Album(0, "LILAC", "아이유(IU)", R.drawable.img_album_exp2),
            Album(1, "NEXT LEVEL", "aespa", R.drawable.img_album_exp3),
            Album(3, "MAP OF THE SOUL", "BTS", R.drawable.img_album_exp4),
            Album(4, "BAAM", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5),
            Album(5, "Weekend", "태연(TAEYEON)", R.drawable.img_album_exp6),
            Album(6, "SWITCH", "IVE", R.drawable.img_album_heya),
            Album(7, "Love Wins All", "Various Artists", R.drawable.img_album_lovewinsall)
        )
        val songDB = SongDatabase.getInstance(this)!!
        albums.forEach{
            songDB.albumDao().insert(it)
        }
    }

    private fun inputDummySongs(){

    }

}

