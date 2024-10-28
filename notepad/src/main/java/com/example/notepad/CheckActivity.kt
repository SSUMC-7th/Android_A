package com.example.notepad


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check) // 레이아웃 설정

        val textViewMemo: TextView = findViewById(R.id.textViewMemo)
        val btnBack: Button = findViewById(R.id.btnBack)

        // MainActivity에서 전달된 메모 내용 표시
        val memo = intent.getStringExtra("memo") ?: ""
        textViewMemo.text = memo

        // 작성 화면(MainActivity)로 돌아가는 버튼 클릭 이벤트 처리
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // 기존 MainActivity를 재사용하고 그 위의 액티비티를 제거
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}