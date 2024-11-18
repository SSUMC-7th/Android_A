package com.example.mock

import javax.sound.sampled.AudioSystem

private fun getDuration(id: Long) = MockStorage.getSoundFile(id)?.let { file ->
    val audioInputStream = AudioSystem.getAudioInputStream(file)
    val format = audioInputStream.format
    val frames = audioInputStream.frameLength
    val duration = frames / format.frameRate
    duration.toInt()
} ?: 0

fun MusicContent.toResponse(): MusicContentResponse = MusicContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = MockDatabase.getAlbumById(albumId)!!.imageId,
    length = getDuration(id),
    albumId = albumId,
    index = index,
    label = label,
    lyrics = lyrics,
)

fun Album.toContentResponse(): AlbumContentResponse = AlbumContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    imageId = imageId,
    releaseDate = releaseDate,
    type = type,
    genre = genre,
)

fun Album.toResponse(): AlbumResponse = AlbumResponse(
    id = id,
    title = title,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    releaseDate = releaseDate,
)

fun PodcastContent.toResponse(): PodcastContentResponse = PodcastContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    length = getDuration(id),
    description = description,
)

fun VideoContent.toResponse(): VideoContentResponse = VideoContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    length = 100,  /* TODO */
)

fun Author.toResponse(): AuthorResponse = AuthorResponse(
    id = id,
    name = name,
    imageId = imageId,
)

fun User.toResponse() = UserResponse(
    id = id,
    email = email
)