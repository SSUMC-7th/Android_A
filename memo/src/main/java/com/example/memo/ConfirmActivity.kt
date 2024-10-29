package com.example.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.databinding.ActivityConfirmBinding

class ConfirmActivity: AppCompatActivity() {
    private lateinit var binding: ActivityConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewMemo.text = intent.getStringExtra("memo")
        binding.buttonBack.setOnClickListener { finish() }
    }
}