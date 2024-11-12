package umc.study.umc_7th.data.source.local

import umc.study.umc_7th.R

// SongDao의 Mock 구현
class MockSongDao : SongDao {
    private val dummySongs = listOf(
        Song(
            id = 1,
            title = "LILAC",
            singer = "아이유",
            playTime = 200,
            coverImg = R.drawable.img_album_exp2,
            albumIdx = 1
        ),
        Song(
            id = 2,
            title = "Flu",
            singer = "아이유",
            playTime = 180,
            coverImg = R.drawable.img_album_exp2,
            albumIdx = 1
        ),
        Song(
            id = 3,
            title = "Butter",
            singer = "방탄소년단",
            playTime = 210,
            coverImg = R.drawable.img_album_exp,
            albumIdx = 2
        ),
        Song(
            id = 4
        ),
        Song(
            id = 5,
            title = "",
            singer = "에스파",
            playTime = 220,
            coverImg = R.drawable.img_album_exp3,
            albumIdx = 3
        ),
        Song(
            id = 7,
            title = "Next Level",
            singer = "에스파",
            playTime = 210,
            coverImg = R.drawable.img_album_exp3,
            albumIdx = 3
        ),
        Song(
            id = 8,
            title = "Bboom Bboom",
            singer = "모모랜드",
            playTime = 190,
            coverImg = R.drawable.img_album_exp5,
            albumIdx = 5
        )
    )

    override suspend fun insertSong(song: Song) {
        // 실제 동작을 하지 않음
    }

    override suspend fun updateSong(song: Song) {
        // 실제 동작을 하지 않음
    }

    override suspend fun deleteSong(song: Song) {
        // 실제 동작을 하지 않음
    }

    override suspend fun getSongById(songId: Int): Song? {
        return dummySongs.find { it.id == songId }
    }

    override suspend fun getAllSongs(): List<Song> {
        return dummySongs
    }

    override suspend fun getSongsByAlbumId(albumIdx: Int): List<Song> {
        return dummySongs.filter { it.albumIdx == albumIdx }
    }
}
