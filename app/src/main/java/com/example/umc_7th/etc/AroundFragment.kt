package com.example.umc_7th.etc

import android.annotation.SuppressLint
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun aroundFragment(navController: NavController){
    Scaffold {
        Text(text = "around")
    }
}