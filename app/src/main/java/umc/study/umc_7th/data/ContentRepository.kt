package umc.study.umc_7th.data

import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.local.LocalDatabase
import umc.study.umc_7th.data.network.Server

class ContentRepository(
    private val server: Server,
    private val database: LocalDatabase,
) {
    suspend fun getRandomMusics(size: Int) = server.getRandomMusics(size)
    suspend fun getRandomPodcasts(size: Int) = server.getRandomPodcasts(size)
    suspend fun getRandomVideos(size: Int) = server.getRandomVideos(size)
    suspend fun getRandomAlbums(size: Int) = server.getRandomAlbums(size)

    suspend fun getMusic(id: Long, refresh: Boolean = true) = if (!refresh)
        database.getSavedMusic(id) ?: throw NoSuchElementException("Music not found")
    else try {
        server.getMusic(id)
    } catch (_: Exception) {
        database.getSavedMusic(id) ?: throw NoSuchElementException("Music not found")
    }

    suspend fun getPodcast(id: Long) = server.getPodcast(id)
    suspend fun getVideo(id: Long) = server.getVideo(id)
    suspend fun getAlbum(id: Long) = server.getAlbum(id)

    fun getAllSavedMusicsFlow() = database.getAllSavedMusicsFlow()
    fun getAllLikedContentsFlow() = database.getAllLikedContentsFlow()

    suspend fun isLiked(id: Long) = database.isLiked(id)
    suspend fun like(vararg ids: Long) = database.like(*ids)
    suspend fun unlike(vararg ids: Long) = database.unlike(*ids)

    suspend fun saveMusic(music: MusicContent) = database.addSavedMusic(music)
    suspend fun deleteMusic(music: MusicContent) = database.deleteSavedMusic(music)
}