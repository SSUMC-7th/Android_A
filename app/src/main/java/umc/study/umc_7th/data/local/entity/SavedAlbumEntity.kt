package umc.study.umc_7th.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import umc.study.umc_7th.AlbumContent
import java.time.LocalDate

@Entity(tableName = "saved_album")
data class SavedAlbumEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val author: String,
    val imageId: Long,
    val type: String,
    val genre: String,
    val releasedDate: LocalDate,
    val contentList: String,
) {
    companion object {
        fun fromContent(content: AlbumContent) = SavedAlbumEntity(
            id = content.id,
            title = content.title,
            author = content.author,
            imageId = content.imageId,
            releasedDate = content.releasedDate,
            contentList = Json.encodeToString(content.contentList),
            type = content.type,
            genre = content.genre,
        )
    }

    fun toContent() = AlbumContent(
        id = id,
        title = title,
        author = author,
        imageId = imageId,
        releasedDate = releasedDate,
        contentList = Json.decodeFromString(contentList),
        type = type,
        genre = genre,
    )
}