package umc.study.umc_7th.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import umc.study.umc_7th.MusicContent

@Entity(tableName = "saved_music")
data class SavedMusicEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val author: String,
    val imageId: Long,
    val length: Int,
    val albumId: Long,
    val label: String?,
) {
    companion object {
        fun fromContent(content: MusicContent) = SavedMusicEntity(
            id = content.id,
            title = content.title,
            author = content.author,
            imageId = content.imageId,
            length = content.length,
            albumId = content.albumId,
            label = content.label,
        )
    }

    fun toContent() = MusicContent(
        id = id,
        title = title,
        author = author,
        imageId = imageId,
        length = length,
        albumId = albumId,
        label = label,
    )
}