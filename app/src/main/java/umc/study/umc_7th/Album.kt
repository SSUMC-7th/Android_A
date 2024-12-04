package umc.study.umc_7th

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    // album의 pk는 임의로 지정해 줄 것이므로, autoGenerate 하지 않음
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var title: String? = "",
    var singer: String? = "",
    var coverImage: Int? = null
)
