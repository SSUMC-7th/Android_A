package memo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    // 전역 상태 변수로 contentState와 showDialogState 선언
    private var savedNote = ""
    private var contentState = mutableStateOf("")
    private var showDialogState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApp()
        }
    }

    override fun onResume() {
        super.onResume()
        if (savedNote.isNotEmpty()) {
            contentState.value = savedNote
        }
    }

    override fun onPause() {
        super.onPause()
        savedNote = contentState.value
    }

    override fun onRestart() {
        super.onRestart()
        showDialogState.value = true
    }

    @Composable
    fun NoteApp() {
        if (showDialogState.value) {
            RestartDialog(
                onConfirm = {
                    contentState.value = ""
                    savedNote = ""
                    showDialogState.value = false
                },
                onDismiss = { showDialogState.value = false }
            )
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
                startActivity(intent)
            }) {
                Text("확인 화면으로 이동")
            }
        }
    }

    @Composable
    fun RestartDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("다시 작성") },
            text = { Text("이전 메모를 다시 작성하시겠습니까?") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("아니오")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("예")
                }
            }
        )
    }
}


