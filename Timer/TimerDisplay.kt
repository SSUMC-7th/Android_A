package com.example.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerDisplay(
    formattedTime : String,
    onStartClick : () -> Unit,
    onPauseClick : () -> Unit,
    onResetClick : () -> Unit,
){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = formattedTime,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onStartClick,
                modifier = Modifier.padding(10.dp)){
                Text("Start")
            }
            Button(onPauseClick,
                modifier = Modifier.padding(10.dp)){
                Text("Pause")
            }
            Button(onResetClick,
                modifier = Modifier.padding(10.dp)){
                Text("Reset")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerDisplayPreview(){
    TimerDisplay(
        formattedTime = "00:00,000",
        onStartClick = {
            println("Start")
        },
        onPauseClick = {
            println("Pause")
        },
        onResetClick = {
            println("Reset")
        }
    )
}
