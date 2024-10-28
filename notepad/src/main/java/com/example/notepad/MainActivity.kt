package com.example.notepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextMemo: EditText
    private var memoContent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 화면 설정

        editTextMemo = findViewById(R.id.editTextMemo)
        val btnNext: Button = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val memo = editTextMemo.text.toString()
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra("memo", memo)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (memoContent.isNotEmpty()) {
            editTextMemo.setText(memoContent)  // 전역 변수 내용 복원
        }
    }

    override fun onPause() {
        super.onPause()
        memoContent = editTextMemo.text.toString()  // 작성 내용 저장
    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this)
            .setMessage("다시 작성하시겠습니까?")
            .setPositiveButton("예") { _, _ -> }
            .setNegativeButton("아니오") { _, _ -> memoContent = "" } // 내용 비우기
            .show()
    }
}