package Memo

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

class DisplayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Intent에서 메모 내용을 가져옴
        val noteContent = intent.getStringExtra("noteContent") ?: ""

        setContent {
            DisplayScreen(noteContent)
        }
    }
    @Composable
    fun DisplayScreen(noteContent: String) {
        var showDialog by remember { mutableStateOf(false) }

        if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("이어 작성") },
                text = { Text("이어 작성하시겠습니까?") },
                confirmButton = {
                    Button(onClick = {
                        val intent = Intent(this@DisplayActivity, MainActivity::class.java).apply{
                            putExtra("noteContent", noteContent)
                        }
                        startActivity(intent)
                        showDialog = false }) {
                        Text("네")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        val intent = Intent(this@DisplayActivity, MainActivity::class.java).apply{
                            putExtra("clearMemo", true)
                        }
                        startActivity(intent)
                        showDialog = false }) {
                        Text("아니오")
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Memo Content",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                Button(
                    modifier=Modifier.fillMaxWidth(),
                    onClick ={showDialog = true}
                ){Text(text = noteContent)}
            }
        }
    }

}