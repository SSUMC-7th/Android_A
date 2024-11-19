package umc.study.umc_7th.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import umc.study.umc_7th.data.local.entity.SavedAlbumEntity

@Dao
interface SavedAlbumDao {
    @Insert
    suspend fun insert(album: SavedAlbumEntity)

    @Query("SELECT * FROM saved_album WHERE id = :id")
    suspend fun get(id: Long): SavedAlbumEntity?

    @Delete
    suspend fun delete(album: SavedAlbumEntity)

    @Query("SELECT * FROM saved_album")
    fun getFlow(): Flow<List<SavedAlbumEntity>>
}