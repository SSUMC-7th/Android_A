package Timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import Timer.ui.theme.Umc_7thTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                TimerView()
            }
        }
    }
}

@Composable
fun TimerView() {
    var time by remember { mutableStateOf(0L) } // Time in milliseconds
    var isRunning by remember { mutableStateOf(false) } // Timer running state

    // LaunchedEffect to update the timer when it's running
    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L) // Delay for 1 second
            time += 10L
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Display the time
        Text(
            text = String.format(
                    "%02d:%02d;%02d",
                    (time / 60000), // Minutes
                    (time / 1000) % 60, // Seconds
                    (time / 10) % 100  // Milliseconds
                    ),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Row {
            // Start/Pause button
            Button(
                onClick = { isRunning = !isRunning},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isRunning) "Pause" else "Start")
            }

            // Clear button
            Button(
                onClick = {
                    isRunning = false
                    time = 0L
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Clear")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    Umc_7thTheme {
        TimerView()
    }
}