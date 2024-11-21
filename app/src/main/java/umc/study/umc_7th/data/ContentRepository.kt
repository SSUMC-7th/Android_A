package umc.study.umc_7th.data

import umc.study.umc_7th.AlbumContent
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

    suspend fun getMusic(id: Long) = server.getMusic(id)
    suspend fun getPodcast(id: Long) = server.getPodcast(id)
    suspend fun getVideo(id: Long) = server.getVideo(id)
    suspend fun getAlbum(id: Long) = server.getAlbum(id)

    fun getAllSavedMusicsFlow() = database.getAllSavedMusicsFlow()
    fun getAllSavedAlbumsFlow() = database.getAllSavedAlbumsFlow()

    suspend fun getAllLikeLog() = server.getAllLikeLogs()
    suspend fun isLiked(id: Long) = server.getLikeLog(id) != null
    suspend fun setLike(id: Long, setTo: Boolean) = server.setLike(id, setTo)
    suspend fun getLikeLog(id: Long) = server.getLikeLog(id)

    suspend fun saveMusic(music: MusicContent) = database.addSavedMusic(music)
    suspend fun getSavedMusic(id: Long) = database.getSavedMusic(id)
    suspend fun deleteMusic(music: MusicContent) = database.deleteSavedMusic(music)

    suspend fun saveAlbum(album: AlbumContent) = database.addSavedAlbum(album)
    suspend fun getSavedAlbum(id: Long) = database.getSavedAlbum(id)
    suspend fun deleteAlbum(album: AlbumContent) = database.deleteSavedAlbum(album)
}