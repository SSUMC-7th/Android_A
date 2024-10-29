package com.example.memo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDone.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java).also {
                it.putExtra("memo", binding.editTextMemo.text.toString())
            }
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (binding.editTextMemo.text.isNotEmpty()) showDialog()
    }

    @SuppressLint("CommitTransaction")
    private fun showDialog() {
        val dialog = RewriteDialog(
            onYesClicked = { binding.editTextMemo.text.clear() },
            onNoClicked = {}
        )

        supportFragmentManager.beginTransaction()
            .add(dialog, "rewrite")
            .commitAllowingStateLoss()
    }
}
