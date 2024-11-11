package umc.study.umc_7th.data.repository

import umc.study.umc_7th.data.source.local.Album
import umc.study.umc_7th.data.source.local.MockAlbumDao

class MockAlbumRepository(private val mockAlbumDao: MockAlbumDao) : AlbumRepository(mockAlbumDao) {
    // 실제 데이터베이스를 사용하지 않고, MockAlbumDao를 사용하여 더미 데이터 반환
    override suspend fun getAllAlbums(): List<Album> {
        return mockAlbumDao.getAllAlbums()
    }

    override suspend fun getAlbumById(albumId: Int): List<Album>? {
        return mockAlbumDao.getAlbumById(albumId)
    }
}
