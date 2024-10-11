package umc.study.umc_7th.network

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.study.umc_7th.Album
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent

private const val BASE_URL = "http://localhost:8080/"

val retrofitInstance: ServerEndpoint by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(ServerEndpoint::class.java)
}

object Server {
    suspend fun getRandomMusics(size: Int): List<MusicContent> {
        TODO()
    }

    suspend fun getMusicById(id: Long): MusicContent {
        TODO()
    }

    suspend fun getMusicByAlbum(albumId: Long): List<MusicContent> {
        TODO()
    }

    suspend fun getAlbum(id: Long): Album {
        TODO()
    }

    suspend fun getRandomPodcasts(size: Int): List<PodcastContent> {
        TODO()
    }

    suspend fun getPodcast(id: Long): PodcastContent {
        TODO()
    }

    suspend fun getRandomVideos(size: Int): List<VideoContent> {
        TODO()
    }

    suspend fun getVideo(id: Long): VideoContent {
        TODO()
    }

    suspend fun getImage(id: Long): ImageBitmap {
        val response = retrofitInstance.getImage(id)
        val bitmap = BitmapFactory.decodeByteArray(response, 0, response.size)
        return bitmap.asImageBitmap()
    }
}