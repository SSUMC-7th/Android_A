package umc.study.umc_7th.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.local.LocalDatabase
import umc.study.umc_7th.data.network.Server
import java.time.LocalDateTime

class ContentRepository(
    private val server: Server,
    private val database: LocalDatabase,
) {
    private val likeLogs = MutableStateFlow<Map<Long, LocalDateTime>>(emptyMap())

    suspend fun getRandomMusics(size: Int) = server.getRandomMusics(size)
    suspend fun getRandomPodcasts(size: Int) = server.getRandomPodcasts(size)
    suspend fun getRandomVideos(size: Int) = server.getRandomVideos(size)
    suspend fun getRandomAlbums(size: Int) = server.getRandomAlbums(size)

    suspend fun getMusic(id: Long) = server.getMusic(id)
    suspend fun getPodcast(id: Long) = server.getPodcast(id)
    suspend fun getVideo(id: Long) = server.getVideo(id)
    suspend fun getAlbum(id: Long) = server.getAlbum(id)

    fun getAllLikeLogsFlow(): StateFlow<Map<Long, LocalDateTime>> = likeLogs
    fun getAllSavedMusicsFlow(): StateFlow<List<MusicContent>> = database.savedMusicFlow
    fun getAllSavedAlbumsFlow(): StateFlow<List<AlbumContent>> = database.savedAlbumFlow

    suspend fun getAllLikeLogs(refresh: Boolean): Map<Long, LocalDateTime> {
        if (refresh) {
            likeLogs.value = emptyMap()
            val logs = server.getAllLikeLogs().toMap()
            likeLogs.value = logs
        }
        return likeLogs.value
    }

    suspend fun getLikeLog(id: Long, refresh: Boolean): Pair<Long, LocalDateTime>? {
        return if (refresh) server.getLikeLog(id)
        else likeLogs.value[id]?.let { id to it }
    }

    suspend fun isLiked(id: Long, refresh: Boolean): Boolean {
        if (refresh) {
            val log = server.getLikeLog(id)
            if (log != null) likeLogs.value = likeLogs.value.plus(log)
        }
        return server.getLikeLog(id) != null
    }

    suspend fun setLike(id: Long, setTo: Boolean) {
        server.setLike(id, setTo)
        val log = server.getLikeLog(id)
        if (log != null) likeLogs.value = likeLogs.value.plus(log)
        else likeLogs.value = likeLogs.value.minus(id)
    }

    suspend fun saveMusic(music: MusicContent) = database.addSavedMusic(music)
    suspend fun getSavedMusic(id: Long) = database.getSavedMusic(id)
    suspend fun deleteMusic(music: MusicContent) = database.deleteSavedMusic(music)

    suspend fun saveAlbum(album: AlbumContent) = database.addSavedAlbum(album)
    suspend fun getSavedAlbum(id: Long) = database.getSavedAlbum(id)
    suspend fun deleteAlbum(album: AlbumContent) = database.deleteSavedAlbum(album)
}