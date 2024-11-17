package umc.study.umc_7th.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "liked_content")
data class LikedContentEntity(
    @PrimaryKey val id: Long,
    val date: LocalDateTime,
)
