package com.example.mock

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

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
    @Serializable(with = LocalDateSerializer::class) val releaseDate: LocalDate,
)

@Serializable
data class AlbumContentResponse(
    val id: Long,
    val title: String,
    val authorId: Long,
    val imageId: Long,
    @Serializable(with = LocalDateSerializer::class) val releaseDate: LocalDate,
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

@Serializable
data class LikeResponse(
    val userId: Long,
    val contentId: Long,
    @Serializable(with = LocalDateTimeSerializer::class) val date: LocalDateTime,
)

@Serializable
data class JoinRequest(
    val name: String,
    val email: String,
    val password: String,
)

@Serializable
data class JoinResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: JoinResult
)

@Serializable
data class JoinResult(
    val memberId: Int,
    @Serializable(with = LocalDateTimeSerializer::class) val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class) val updatedAt: LocalDateTime,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: LoginResult,
)

@Serializable
data class LoginResult(
    val memberId: Int,
    val accessToken: String,
)

@Serializable
data class TokenTestResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: String,
)