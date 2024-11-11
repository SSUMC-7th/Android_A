package umc.study.umc_7th.data.repository

import umc.study.umc_7th.data.source.local.MockSongDao
import umc.study.umc_7th.data.source.local.Song

class MockSongRepository(private val mockSongDao: MockSongDao) : SongRepository(mockSongDao) {
    // 실제 데이터베이스를 사용하지 않고, MockSongDao를 사용하여 더미 데이터 반환
    override suspend fun getAllSongs(): List<Song> {
        return mockSongDao.getAllSongs()
    }

    override suspend fun getSongById(songId: Int): Song? {
        return mockSongDao.getSongById(songId)
    }

    override suspend fun getSongsByAlbumId(albumIdx: Int): List<Song> {
        return mockSongDao.getSongsByAlbumId(albumIdx)
    }
}
