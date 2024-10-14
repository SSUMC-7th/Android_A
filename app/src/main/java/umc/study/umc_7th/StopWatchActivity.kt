import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*

class StopwatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerScreen()
        }
    }
}

@Composable
fun TimerScreen() {
    // 상태 변수 선언
    var timeInMillis by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()  // CoroutineScope 유지
    var job by remember { mutableStateOf<Job?>(null) }  // Job 관리용

    // UI 레이아웃 구성
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 시간 표시 Text
        Text(
            text = formatTime(timeInMillis),
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 버튼 레이아웃
        Row {
            // Start/Pause 버튼
            Button(onClick = {
                if (isRunning) {
                    stopTimer(job)  // 타이머 멈춤
                } else {
                    startTimer(coroutineScope, timeInMillis) { elapsedTime ->
                        timeInMillis = elapsedTime
                    }
                }
                isRunning = !isRunning  // 실행 상태 반전
            }) {
                Text(if (isRunning) "Pause" else "Start")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Clear 버튼
            Button(onClick = {
                stopTimer(job)  // 타이머 멈춤
                timeInMillis = 0L  // 시간 초기화
                isRunning = false  // 상태 초기화
            }) {
                Text("Clear")
            }
        }
    }
}

// 타이머 시작 함수
fun startTimer(
    scope: CoroutineScope,
    currentTime: Long,
    onTick: (Long) -> Unit
): Job {
    val startTime = System.currentTimeMillis() - currentTime
    return scope.launch {
        while (isActive) {
            val elapsedTime = System.currentTimeMillis() - startTime
            onTick(elapsedTime)  // 시간 업데이트
            delay(10L)  // 10ms마다 업데이트
        }
    }
}

// 타이머 정지 함수
fun stopTimer(job: Job?) {
    job?.cancel()  // Coroutine 취소
}

// 시간 포맷팅 함수: mm:ss.SS 형식
fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    val milliseconds = (timeInMillis % 1000) / 10  // 두 자리 밀리초

    return String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
}

// Android Studio에서 UI 미리보기 기능
@Preview(showBackground = true)
@Composable
fun PreviewTimerScreen() {
    TimerScreen()
}