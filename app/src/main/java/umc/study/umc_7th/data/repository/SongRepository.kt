package umc.study.umc_7th.data.repository

import umc.study.umc_7th.data.source.local.Song
import umc.study.umc_7th.data.source.local.SongDao
import javax.inject.Inject

open class SongRepository @Inject constructor(private val songDao: SongDao) {

    // Room에서 데이터를 가져오는 메서드
    suspend fun insertSong(song: Song) {
        songDao.insertSong(song)
    }

    suspend fun updateSong(song: Song) {
        songDao.updateSong(song)
    }

    suspend fun deleteSong(song: Song) {
        songDao.deleteSong(song)
    }

    open suspend fun getSongById(songId: Int): Song? {
        return songDao.getSongById(songId)
    }

    open suspend fun getAllSongs(): List<Song> {
        return songDao.getAllSongs()
    }

    open suspend fun getSongsByAlbumId(albumIdx: Int): List<Song> {
        return songDao.getSongsByAlbumId(albumIdx)
    }
}