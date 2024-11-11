package umc.study.umc_7th.data.repository

import umc.study.umc_7th.data.source.local.Album
import umc.study.umc_7th.data.source.local.AlbumDao
import javax.inject.Inject

open class AlbumRepository @Inject constructor(private val albumDao: AlbumDao) {

    // Room에서 데이터를 가져오는 메서드
    suspend fun insertAlbum(album: Album) {
        albumDao.insertAlbum(album)
    }

    suspend fun updateAlbum(album: Album) {
        albumDao.updateAlbum(album)
    }

    suspend fun deleteAlbum(album: Album) {
        albumDao.deleteAlbum(album)
    }

    open suspend fun getAlbumById(albumId: Int): List<Album>? {
        return albumDao.getAlbumById(albumId)
    }

    open suspend fun getAllAlbums(): List<Album> {
        return albumDao.getAllAlbums()
    }
}