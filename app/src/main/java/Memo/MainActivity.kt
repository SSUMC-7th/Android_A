package Memo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    // 메모 내용을 저장할 전역 변수
    private var savedNote = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApp()
        }
    }

    override fun onResume() {
        super.onResume()
        // onResume에서 저장된 내용이 있다면 복원
        if (savedNote.isNotEmpty()) {
            contentState.value = savedNote
        }
    }

    override fun onPause() {
        super.onPause()
        // 현재 메모 내용을 전역 변수에 저장
        savedNote = contentState.value
    }

    override fun onRestart() {
        super.onRestart()
        // 다시 작성 여부를 묻는 다이얼로그 띄우기
        showDialogState.value = true

    }

    private val contentState = mutableStateOf("")
    private val showDialogState = mutableStateOf(false)

    @Composable
    fun NoteApp() {
        if (showDialogState.value) {
            RestartDialog()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = contentState.value,
                onValueChange = { contentState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {

                val intent = Intent(this@MainActivity, DisplayActivity::class.java).apply {
                    putExtra("noteContent", contentState.value)
                }
                startActivity(intent)}
            ) {
                Text("확인 화면으로 이동")
            }
        }
    }

    @Composable
    fun RestartDialog() {
        AlertDialog(
            onDismissRequest = { showDialogState.value = false },
            title = { Text("다시 작성") },
            text = { Text("이전 메모를 다시 작성하시겠습니까?") },
            confirmButton = {
                Button(onClick = {
                    savedNote = ""
                    contentState.value = ""
                    showDialogState.value = false
                }) {
                    Text("아니오")
                }
            },
            dismissButton = {
                Button(onClick = { showDialogState.value = false }) {
                    Text("예")
                }
            }
        )
    }
}




