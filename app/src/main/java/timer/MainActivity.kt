package timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import umc.study.umc_7th.ui.theme.Umc_7thTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                App()
            }
        }
    }
}

@Composable
@Preview
fun App(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        val stopWatch = remember { StopWatch()}
        TimerDisplay(formattedTime =stopWatch.formattedTime,
            onStartClick = { stopWatch.start() },
            onPauseClick = stopWatch::pause,
            onResetClick = stopWatch::reset)
    }

}
