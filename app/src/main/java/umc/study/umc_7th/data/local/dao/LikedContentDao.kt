package umc.study.umc_7th.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import umc.study.umc_7th.data.local.entity.LikedContentEntity

@Dao
interface LikedContentDao {
    @Insert
    suspend fun insert(entity: LikedContentEntity)

    @Query("SELECT * FROM liked_content WHERE id = :id")
    suspend fun get(id: Long): List<LikedContentEntity>

    @Query("DELETE FROM liked_content WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM liked_content")
    fun getFlow(): Flow<List<LikedContentEntity>>
}

