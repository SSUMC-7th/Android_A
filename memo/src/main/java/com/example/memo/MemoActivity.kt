package com.example.memo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.memo.ui.theme.Umc_7thTheme

class NoteViewModel : ViewModel() {
    // 메모 내용 저장용 전역 변수
    var noteContent: String by mutableStateOf("")
    var isRestartDialogShown by mutableStateOf(false)
}

class MemoActivity : ComponentActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Umc_7thTheme {
                MyApp(noteViewModel = noteViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 전역 변수에 저장된 내용이 있을 경우 TextField에 표시
        if (noteViewModel.noteContent.isNotEmpty()) {
            noteViewModel.noteContent = noteViewModel.noteContent
        }
    }

    override fun onPause() {
        super.onPause()
        // 현재 TextField의 내용을 전역 변수에 저장
        noteViewModel.noteContent = noteViewModel.noteContent
    }

    override fun onRestart() {
        super.onRestart()
        // 앱 재시작 시 Dialog 표시
        noteViewModel.isRestartDialogShown = true
    }
}

@Composable
fun MyApp(noteViewModel: NoteViewModel) {
    MaterialTheme {
        if (noteViewModel.isRestartDialogShown) {
            RestartDialog(
                onDismiss = { noteViewModel.isRestartDialogShown = false },
                onConfirm = {
                    noteViewModel.noteContent = ""
                    noteViewModel.isRestartDialogShown = false
                }
            )
        }
        NoteScreen(noteViewModel = noteViewModel)
    }
}

@Composable
fun NoteScreen(noteViewModel: NoteViewModel) {
    var text by remember { mutableStateOf(noteViewModel.noteContent) }
    var isViewingNote by remember { mutableStateOf(false) }

    LaunchedEffect(noteViewModel.noteContent) {
        text = noteViewModel.noteContent
    }

    if (isViewingNote) {
        // 확인 화면 (메모 내용 보기)
        ConfirmationScreen(
            noteContent = text,
            onEdit = { isViewingNote = false }
        )
    } else {
        // 메모 작성 화면
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter your note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(750.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    noteViewModel.noteContent = text
                    isViewingNote = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Done")
            }
        }
    }
}

@Composable
fun ConfirmationScreen(noteContent: String, onEdit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Note",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = noteContent,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
            Text("Edit Note")
        }
    }
}

@Composable
fun RestartDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        },
        title = { Text("Rewrite Note") },
        text = { Text("Would you like to start over?") }
    )
}

@Preview
@Composable
fun PreviewMyApp() {
    Umc_7thTheme {
        MyApp(noteViewModel = NoteViewModel())
    }
}