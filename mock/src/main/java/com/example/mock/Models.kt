package com.example.mock

import kotlinx.serialization.Serializable

@Serializable
sealed interface Content {
    val id: Long
    val title: String
    val authorId: Long
    val imageId: Long
    val length: Int // 단위: 초
}

@Serializable
data class MusicContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
    val albumId: Long,
): Content

@Serializable
data class Album(
    val id: Long,
    val title: String,
    val authorId: Long,
    val imageId: Long,
    val releaseDate: String,
    val type: String,
    val genre: String,
)

@Serializable
data class PodcastContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
    val description: String,
): Content

@Serializable
data class VideoContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    override val length: Int,
): Content

@Serializable
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
)

@Serializable
data class Author(
    val id: Long,
    val name: String,
    val imageId: Long? = null,
)