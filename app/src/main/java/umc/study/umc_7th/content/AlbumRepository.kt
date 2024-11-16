package umc.study.umc_7th.content

class AlbumRepository(private val albumDao: AlbumDao) {
    suspend fun insertAlbum(album: AlbumContent) {
        val existingAlbum = albumDao.getAlbumByTitle(album.albumTitle)

        if(existingAlbum == null){
            albumDao.insertAlbum(album)
            albumDao.getAlbumByTitle(album.albumTitle)!!
        }else{
            existingAlbum
        }
    }

    suspend fun getAllAlbums(): List<AlbumContent>{
        return albumDao.getAllAlbums()
    }
    suspend fun getAlbumByTitle(title: String): AlbumContent{
        return albumDao.getAlbumByTitle(title)
    }

    suspend fun updateAlbum(album: AlbumContent){
        albumDao.updateAlbum(album)
    }

    suspend fun getAllLikedAlbums(): List<AlbumContent>{
        return albumDao.getAllLikeAlbum()
    }


}