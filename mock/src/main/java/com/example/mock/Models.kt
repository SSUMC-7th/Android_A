package com.example.mock

import kotlinx.serialization.Serializable

@Serializable
sealed interface HasImage {
    val imageId: Long?
}

@Serializable
sealed interface Content {
    val id: Long
    val title: String
    val authorId: Long
}

@Serializable
data class MusicContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    val albumId: Long,
    val index: Int,
    val label: String?,
    val lyrics: String?,
): Content

@Serializable
data class Album(
    val id: Long,
    val title: String,
    val authorId: Long,
    override val imageId: Long,
    val releaseDate: String,
    val type: String,
    val genre: String,
): HasImage

@Serializable
data class PodcastContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
    val description: String,
): Content, HasImage

@Serializable
data class VideoContent(
    override val id: Long,
    override val title: String,
    override val authorId: Long,
    override val imageId: Long,
): Content, HasImage

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
    override val imageId: Long?,
): HasImage