package umc.study.umc_7th.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MusicContentResponse(
    val id: Long,
    val title: String,
    val authorId: Long,
    val author: String,
    val imageId: Long,
    val length: Int,
    val albumId: Long,
    val index: Int,
    val label: String? = null,
    val lyrics: String? = null,
)

@Serializable
data class AlbumResponse(
    val id: Long,
    val title: String,
    val author: String,
    val imageId: Long,
    val releaseDate: String,
)

@Serializable
data class AlbumContentResponse(
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
    val id: Long,
    val title: String,
    val authorId: Long,
    val author: String,
    val imageId: Long,
    val length: Int,
    val description: String,
)

@Serializable
data class VideoContentResponse(
    val id: Long,
    val title: String,
    val authorId: Long,
    val author: String,
    val imageId: Long,
    val length: Int,
)

@Serializable
data class AuthorResponse(
    val id: Long,
    val name: String,
    val imageId: Long? = null,
)

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)

@Serializable
data class UserResponse(
    val id: Long,
    val email: String,
)