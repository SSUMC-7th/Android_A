package com.example.timer

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.timer.ui.theme.Umc_7thTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerActivity : ComponentActivity() {
    private val timeState = MutableStateFlow(0L)
    private val isCountingState = MutableStateFlow(false)
    private var timerJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val time by timeState.asStateFlow().collectAsStateWithLifecycle()
                        val isCounting by isCountingState.asStateFlow().collectAsStateWithLifecycle()

                        TimerScreen(
                            millisecond = time,
                            isCounting = isCounting,
                            onClearClicked = { onClearClicked() },
                            onPauseClicked = { onPauseClicked() },
                            onStartClicked = { onStartClicked() },
                        )
                    }
                }
            }
        }
    }

    private fun onClearClicked() {
        timerJob?.cancel()
        timeState.value = 0L
        isCountingState.value = false
    }

    private fun onPauseClicked() {
        timerJob?.cancel()
        isCountingState.value = false
    }

    private fun onStartClicked() {
        timerJob = lifecycleScope.launch {
            val startTime = System.currentTimeMillis()
            while (true) {
                timeState.value = System.currentTimeMillis() - startTime
                delay(10)
            }
        }
        isCountingState.value = true
    }
}

@Composable
fun TimerScreen(
    millisecond: Long,
    isCounting: Boolean,
    onStartClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    onClearClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = buildAnnotatedString {
                    val minute = millisecond / (1000 * 60)
                    val second = millisecond % (1000 * 60) / (1000)
                    val under = millisecond % (1000 * 60) % (1000) / (10)
                    withStyle(style = SpanStyle(fontSize = 64.sp)) {
                        append("%02d".format(minute))
                        append(":")
                        append("%02d".format(second))
                        append("'")
                        append("%02d".format(under))
                    }
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 32.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        ) {
            Button(
                onClick = if (isCounting) onPauseClicked else onStartClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCounting) Color.Red else Color.Black,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = if (isCounting) "PAUSE" else "START",
                    fontSize = 24.sp,
                )
            }
            Button(
                onClick = onClearClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = "CLEAR",
                    fontSize = 24.sp,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewTimerScreen() {
    Umc_7thTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TimerScreen(
                    millisecond = 123456L,
                    isCounting = false,
                    onClearClicked = {},
                    onPauseClicked = {},
                    onStartClicked = {},
                )
            }
        }
    }
}
