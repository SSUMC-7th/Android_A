package com.example.mock

fun MusicContent.toResponse(): MusicContentResponse = MusicContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    length = length,
    albumId = albumId,
    index = index,
    label = label,
    lyrics = lyrics,
)

fun Album.toResponse(): AlbumResponse = AlbumResponse(
    id = id,
    title = title,
    authorId = authorId,
    imageId = imageId,
    releaseDate = releaseDate,
    type = type,
    genre = genre,
)

fun PodcastContent.toResponse(): PodcastContentResponse = PodcastContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    length = length,
    description = description,
)

fun VideoContent.toResponse(): VideoContentResponse = VideoContentResponse(
    id = id,
    title = title,
    authorId = authorId,
    author = MockDatabase.getAuthorById(authorId)!!.name,
    imageId = imageId,
    length = length,
)

fun Author.toResponse(): AuthorResponse = AuthorResponse(
    id = id,
    name = name,
    imageId = imageId,
)
