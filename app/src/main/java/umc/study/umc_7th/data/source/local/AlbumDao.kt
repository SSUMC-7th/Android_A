package umc.study.umc_7th.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("SELECT * FROM albumTable WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Int): List<Album>?

    @Query("SELECT * FROM albumTable")
    suspend fun getAllAlbums(): List<Album>
}