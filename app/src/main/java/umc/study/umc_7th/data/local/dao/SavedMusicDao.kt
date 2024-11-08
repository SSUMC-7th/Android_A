package umc.study.umc_7th.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import umc.study.umc_7th.data.local.entity.SavedMusicEntity

@Dao
interface SavedMusicDao {
    @Insert
    suspend fun insert(music: SavedMusicEntity)

    @Insert
    suspend fun insert(vararg music: SavedMusicEntity)

    @Query("SELECT * FROM saved_music WHERE id = :id")
    suspend fun get(id: Long): List<SavedMusicEntity>

    @Query("SELECT * FROM saved_music WHERE id IN (:id)")
    suspend fun get(id: List<Long>): List<SavedMusicEntity>

    @Query("DELETE FROM saved_music")
    suspend fun clear()

    @Delete
    suspend fun delete(music: SavedMusicEntity)

    @Query("SELECT * FROM saved_music")
    fun getFlow(): Flow<List<SavedMusicEntity>>
}