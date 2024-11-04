package umc.study.umc_7th

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.study.umc_7th.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) // 여기다가 XML 넣는거임
    }
}