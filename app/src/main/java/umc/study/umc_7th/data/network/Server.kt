package umc.study.umc_7th.data.network

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.Auth
import umc.study.umc_7th.BuildConfig
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.User
import umc.study.umc_7th.VideoContent
import java.time.LocalDate
import javax.inject.Inject

private val retrofitInstance: ServerEndpoint by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build()

    retrofit.create(ServerEndpoint::class.java)
}

class Server @Inject constructor() {
    suspend fun getRandomMusics(size: Int): List<MusicContent> {
        val response = retrofitInstance.getRandomMusics(size)
        return response.map { it.toMusicContent() }
    }

    suspend fun getMusic(id: Long): MusicContent {
        val response = retrofitInstance.getMusics(id = id, albumId = null).first()
        return response.toMusicContent()
    }

    suspend fun getRandomAlbums(size: Int): List<Album> {
        val response = retrofitInstance.getRandomAlbums(size)
        return response.map {
            Album(
                id = it.id,
                title = it.title,
                author = it.author,
                imageId = it.imageId,
                releasedDate = LocalDate.parse(it.releaseDate),
            )
        }
    }

    suspend fun getAlbum(id: Long): AlbumContent {
        val albumResponse = retrofitInstance.getAlbum(id)
        val authorResponse = retrofitInstance.getAuthor(albumResponse.authorId)
        val musicResponseList = retrofitInstance.getMusics(id = null, albumId = id)
        return AlbumContent(
            id = albumResponse.id,
            title = albumResponse.title,
            author = authorResponse.name,
            imageId = albumResponse.imageId,
            type = albumResponse.type,
            genre = albumResponse.genre,
            releasedDate = LocalDate.parse(albumResponse.releaseDate),
            contentList = musicResponseList.map { it.toMusicContent() },
        )
    }

    suspend fun getRandomPodcasts(size: Int): List<PodcastContent> {
        val response = retrofitInstance.getRandomPodcasts(size)
        return response.map { it.toPodcastContent() }
    }

    suspend fun getPodcast(id: Long): PodcastContent {
        val response = retrofitInstance.getPodcast(id)
        return response.toPodcastContent()
    }

    suspend fun getRandomVideos(size: Int): List<VideoContent> {
        val response = retrofitInstance.getRandomVideos(size)
        return response.map { it.toVideoContent() }
    }

    suspend fun getVideo(id: Long): VideoContent {
        val response = retrofitInstance.getVideo(id)
        return response.toVideoContent()
    }

    suspend fun getImage(id: Long): ImageBitmap {
        val response = retrofitInstance.getImage(id)
        val bytes = response.bytes()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return bitmap.asImageBitmap()
    }

    suspend fun signUp(email: String, password: String): Auth {
        val response = retrofitInstance.signup(SignUpRequest(email, password))
        return response.toAuth()
    }

    suspend fun login(email: String, password: String): Auth {
        val response = retrofitInstance.login(LoginRequest(email, password))
        return response.toAuth()
    }

    suspend fun refresh(refreshToken: String): Auth {
        val response = retrofitInstance.refresh(refreshToken)
        return response.toAuth()
    }

    suspend fun getMyProfile(token: String): User {
        val response = retrofitInstance.getMyProfile("Bearer $token")
        return response.toUser()
    }
}