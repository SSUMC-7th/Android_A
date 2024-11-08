package umc.study.umc_7th.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import umc.study.umc_7th.data.local.entity.LikedContentEntity

@Dao
interface LikedContentDao {
    @Insert
    suspend fun insert(entity: LikedContentEntity)

    @Query("SELECT * FROM liked_content WHERE id = :id")
    suspend fun get(id: Long): List<LikedContentEntity>

    @Query("SELECT * FROM liked_content")
    suspend fun getAll(): List<LikedContentEntity>

    @Query("DELETE FROM liked_content WHERE id = :id")
    suspend fun delete(id: Long)
}

