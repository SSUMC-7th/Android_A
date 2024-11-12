package umc.study.umc_7th.data.source.local

import umc.study.umc_7th.R

// AlbumDao의 Mock 구현
class MockAlbumDao : AlbumDao {

    // 더미 데이터 설정
    private val dummyAlbums = listOf(
        Album(
            id = 1,
            title = "IU 5th Album 'LILAC'",
            singer = "아이유 (IU)",
            coverImg = R.drawable.img_album_exp2
        ),
        Album(
            id = 2,
            title = "Butter",
            singer = "방탄소년단 (BTS)",
            coverImg = R.drawable.img_album_exp
        ),
        Album(
            id = 3,
            title = "iScreaM Vol.10 : Next Level Remixes",
            singer = "에스파 (AESPA)",
            coverImg = R.drawable.img_album_exp3
        ),
        Album(
            id = 4,
            title = "MAP OF THE SOUL : PERSONA",
            singer = "방탄소년단 (BTS)",
            coverImg = R.drawable.img_album_exp4
        ),
        Album(
            id = 5,
            title = "GREAT!",
            singer = "모모랜드",
            coverImg = R.drawable.img_album_exp5
        )
    )

    override suspend fun insertAlbum(album: Album) {
        // 실제 동작을 하지 않음
    }

    override suspend fun updateAlbum(album: Album) {
        // 실제 동작을 하지 않음
    }

    override suspend fun deleteAlbum(album: Album) {
        // 실제 동작을 하지 않음
    }

    override suspend fun getAlbumById(albumId: Int): List<Album> {
        return dummyAlbums.filter { it.id == albumId }
    }

    override suspend fun getAllAlbums(): List<Album> {
        return dummyAlbums
    }
}
