package umc.study.umc_7th.network

import kotlinx.serialization.Serializable

@Serializable
sealed interface ContentResponse {
    val id: Long
    val title: String
    val authorId: Long
    val imageId: Long
    val length: Int  // 단위: 초
}

@Serializable
data class MusicContentResponse(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
    val albumId: Long,
    val index: Int,
    val label: String? = null,
    val lyrics: String? = null,
): ContentResponse

@Serializable
data class AlbumResponse(
    val id: Long,
    val title: String,
    val authorId: Long,
    val imageId: Long,
    val releaseDate: String,
    val type: String,
    val genre: String,
)

@Serializable
data class PodcastContentResponse(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
    val description: String,
): ContentResponse

@Serializable
data class VideoContentResponse(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
): ContentResponse

@Serializable
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
)

@Serializable
data class AuthorResponse(
    val id: Long,
    val name: String,
    val imageId: Long?,
)