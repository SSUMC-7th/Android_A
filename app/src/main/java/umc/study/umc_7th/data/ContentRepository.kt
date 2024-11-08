package umc.study.umc_7th.data

import kotlinx.coroutines.flow.Flow
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.data.local.LocalDatabase
import umc.study.umc_7th.data.network.Server

class ContentRepository(
    private val server: Server,
    private val database: LocalDatabase,
) {
    suspend fun getRandomMusics(size: Int): List<MusicContent> = server.getRandomMusics(size)
    suspend fun getRandomPodcasts(size: Int): List<PodcastContent> = server.getRandomPodcasts(size)
    suspend fun getRandomVideos(size: Int): List<VideoContent> = server.getRandomVideos(size)
    suspend fun getRandomAlbums(size: Int): List<Album> = server.getRandomAlbums(size)

    suspend fun getMusic(id: Long): MusicContent = try {
        server.getMusic(id)
    } catch (_: Exception) {
        database.getSavedMusic(id) ?: throw NoSuchElementException("Music not found")
    }

    suspend fun getPodcast(id: Long): PodcastContent = server.getPodcast(id)
    suspend fun getVideo(id: Long): VideoContent = server.getVideo(id)
    suspend fun getAlbum(id: Long): AlbumContent = server.getAlbum(id)

    fun getAllSavedMusicFlow(): Flow<List<MusicContent>> = database.getAllSavedMusicFlow()

    suspend fun isLiked(id: Long): Boolean = database.isLiked(id)
    suspend fun like(vararg ids: Long) = database.like(*ids)
    suspend fun unlike(vararg ids: Long) = database.unlike(*ids)

    suspend fun saveMusic(music: MusicContent) = database.addSavedMusic(music)
    suspend fun deleteMusic(music: MusicContent) = database.deleteSavedMusic(music)
}