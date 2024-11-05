package com.example.insta

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofitInstance: ImageGetter by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://random.imagecdn.app")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build()

    retrofit.create(ImageGetter::class.java)
}

object RetrofitClient {
    suspend fun getImage(): ImageBitmap {
        val response = retrofitInstance.getImage()
        val bytes = response.bytes()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return bitmap.asImageBitmap()
    }
}

interface ImageGetter {
    @GET("/200/200")
    suspend fun getImage(): ResponseBody
}