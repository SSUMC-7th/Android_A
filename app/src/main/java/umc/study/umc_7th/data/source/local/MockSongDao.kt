package umc.study.umc_7th.data.source.local

import umc.study.umc_7th.R

// SongDao의 Mock 구현
class MockSongDao : SongDao {
    private val dummySongs = listOf(
        Song(
            title = "LILAC",
            singer = "아이유",
            playTime = 200,
            coverImg = R.drawable.img_album_exp2,
            albumidx = 1
        ),
        Song(
            title = "Flu",
            singer = "아이유",
            playTime = 180,
            coverImg = R.drawable.img_album_exp2,
            albumidx = 1
        ),
        Song(
            title = "Butter",
            singer = "방탄소년단",
            playTime = 210,
            coverImg = R.drawable.img_album_exp,
            albumidx = 2
        ),
        Song(),
        Song(
            title = "",
            singer = "에스파",
            playTime = 220,
            coverImg = R.drawable.img_album_exp3,
            albumidx = 3
        ),
        Song(
            title = "Next Level",
            singer = "에스파",
            playTime = 210,
            coverImg = R.drawable.img_album_exp3,
            albumidx = 3
        ),
        Song(
            title = "Bboom Bboom",
            singer = "모모랜드",
            playTime = 190,
            coverImg = R.drawable.img_album_exp5,
            albumidx = 5
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
        return dummySongs.filter { it.albumidx == albumIdx }
    }
}
