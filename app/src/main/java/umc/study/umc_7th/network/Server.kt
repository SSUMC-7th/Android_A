package umc.study.umc_7th.network

import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.study.umc_7th.Album
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import java.time.LocalDate

private const val BASE_URL = "http://10.0.2.2:8080/"

val retrofitInstance: ServerEndpoint by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build()

    retrofit.create(ServerEndpoint::class.java)
}

object Server {
    suspend fun getRandomMusics(size: Int): List<MusicContent> {
        val response = retrofitInstance.getRandomMusics(size)
        return response.map {
            MusicContent(
                id = it.id,
                title = it.title,
                author = it.author,
                imageId = it.imageId,
                length = it.length,
                albumId = it.albumId,
            )
        }
    }

    suspend fun getMusic(id: Long): MusicContent? {
        val response = retrofitInstance.getMusics(id = id, albumId = null).firstOrNull()
        return response?.let {
            MusicContent(
                id = it.id,
                title = response.title,
                author = response.author,
                imageId = response.imageId,
                length = response.length,
                albumId = response.albumId,
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAlbum(id: Long): Album {
        val albumResponse = retrofitInstance.getAlbum(id)
        val authorResponse = retrofitInstance.getAuthor(albumResponse.authorId)
        val musicResponseList = retrofitInstance.getMusics(id = null, albumId = id)
        return Album(
            id = albumResponse.id,
            title = albumResponse.title,
            author = authorResponse.name,
            imageId = albumResponse.imageId,
            type = albumResponse.type,
            genre = albumResponse.genre,
            releasedDate = LocalDate.parse(albumResponse.releaseDate),
            contentList = musicResponseList.map {
                MusicContent(
                    id = it.id,
                    title = it.title,
                    author = authorResponse.name,
                    imageId = it.imageId,
                    length = it.length,
                    albumId = id,
                    label = it.label,
                )
            },
        )
    }

    suspend fun getRandomPodcasts(size: Int): List<PodcastContent> {
        val response = retrofitInstance.getRandomPodcasts(size)
        return response.map {
            PodcastContent(
                id = it.id,
                title = it.title,
                author = it.author,
                imageId = it.imageId,
                length = it.length,
                description = it.description,
            )
        }
    }

    suspend fun getPodcast(id: Long): PodcastContent {
        val response = retrofitInstance.getPodcast(id)
        return PodcastContent(
            id = response.id,
            title = response.title,
            author = response.author,
            imageId = response.imageId,
            length = response.length,
            description = response.description,
        )
    }

    suspend fun getRandomVideos(size: Int): List<VideoContent> {
        val response = retrofitInstance.getRandomVideos(size)
        return response.map {
            VideoContent(
                id = it.id,
                title = it.title,
                author = it.author,
                imageId = it.imageId,
                length = it.length,
            )
        }
    }

    suspend fun getVideo(id: Long): VideoContent {
        val response = retrofitInstance.getVideo(id)
        return VideoContent(
            id = response.id,
            title = response.title,
            author = response.author,
            imageId = response.imageId,
            length = response.length,
        )
    }

    suspend fun getImage(id: Long): ImageBitmap {
        val response = retrofitInstance.getImage(id)
        val bytes = response.bytes()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return bitmap.asImageBitmap()
    }
}