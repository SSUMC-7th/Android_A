package umc.study.umc_7th

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import umc.study.umc_7th.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title : String? = null
        var singer : String? = null

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.songMusicTitleTv.text = title
            binding.songSingerNameTv.text = singer
        }

        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }


    }

    fun setPlayerStatus (isPlaying : Boolean){
        if(isPlaying){ // 재생중
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else { // 일시정지
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }
//    override fun onBackPressed() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("message", "뒤로가기 버튼 클릭")
//        setResult(RESULT_OK, intent)
//        finish()
//    }
//
}