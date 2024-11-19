package com.example.mock

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

object MockDatabase {
    private val musics = loadTable("musics") { parts ->
        MusicContent(id = parts[0].toLong(),
            title = parts[1],
            authorId = parts[2].toLong(),
            albumId = parts[3].toLong(),
            index = parts[4].toInt(),
            label = parts[5].takeIf { it.isNotEmpty() },
            lyrics = parts[6].takeIf { it.isNotEmpty() })
    }

    private val albums = loadTable("albums") { parts ->
        Album(
            id = parts[0].toLong(),
            title = parts[1],
            authorId = parts[2].toLong(),
            imageId = parts[3].toLong(),
            releaseDate = LocalDate.parse(parts[4]),
            type = parts[5],
            genre = parts[6]
        )
    }

    private val podcasts = loadTable("podcasts") { parts ->
        PodcastContent(
            id = parts[0].toLong(),
            title = parts[1],
            authorId = parts[2].toLong(),
            imageId = parts[3].toLong(),
            description = parts[4]
        )
    }

    private val videos = loadTable("videos") { parts ->
        VideoContent(
            id = parts[0].toLong(),
            title = parts[1],
            authorId = parts[2].toLong(),
            imageId = parts[3].toLong()
        )
    }

    private val authors = loadTable("authors") { parts ->
        Author(
            id = parts[0].toLong(),
            name = parts[1],
            imageId = parts[2].takeIf { it.isNotEmpty() }?.toLong()
        )
    }

    private val users = loadTable("users") { parts ->
        User(
            id = parts[0].toLong(),
            email = parts[1],
            password = parts[2],
        )
    }

    private val likes = loadTable("likes") { parts ->
        Like(
            userId = parts[0].toLong(),
            contentId = parts[1].toLong(),
            date = LocalDateTime.parse(parts[2]),
        )
    }

    fun getMusicById(id: Long): MusicContent? = musics.find { it.id == id }
    fun getAlbumById(id: Long): Album? = albums.find { it.id == id }
    fun getPodcastById(id: Long): PodcastContent? = podcasts.find { it.id == id }
    fun getVideoById(id: Long): VideoContent? = videos.find { it.id == id }

    fun getAuthorById(id: Long): Author? = authors.find { it.id == id }

    fun getUserById(id: Long): User? = users.find { it.id == id }
    fun getUserByEmail(email: String): User? = users.find { it.email == email }

    fun getRandomMusicList(size: Int): List<MusicContent> = List(size) { musics.random() }
    fun getRandomAlbumList(size: Int): List<Album> = List(size) { albums.random() }
    fun getRandomPodcastList(size: Int): List<PodcastContent> = List(size) { podcasts.random() }
    fun getRandomVideoList(size: Int): List<VideoContent> = List(size) { videos.random() }

    fun getMusicsByAlbum(albumId: Long): List<MusicContent> =
        musics.filter { it.albumId == albumId }

    fun getAllLikes(userId: Long): List<Like> = likes.filter { it.userId == userId }
    fun getLike(userId: Long, contentId: Long): Like? =
        likes.find { it.userId == userId && it.contentId == contentId }

    @Synchronized
    fun createUser(email: String, password: String): User? {
        // 이메일 중복 체크
        if (getUserByEmail(email) != null) return null

        val newUser = User(
            id = (users.maxOfOrNull { it.id } ?: 0) + 1,
            email = email,
            password = password,
        )

        users.add(newUser)
        return newUser
    }

    @Synchronized
    fun addLike(userId: Long, contentId: Long) {
        val like = Like(userId, contentId, LocalDateTime.now())
        likes.add(like)
    }

    @Synchronized
    fun cancelLike(userId: Long, contentId: Long) {
        likes.removeIf { it.userId == userId && it.contentId == contentId }
    }
}

private fun <T> loadTable(
    tableName: String, mapper: (List<String>) -> T
): MutableList<T> {
    val result = mutableListOf<T>()
    val filePath = "${System.getProperty("user.dir")}/mock/src/main/java/com/example/mock/tables"
    val reader = File(filePath, "${tableName}.csv")

    reader.useLines { lines ->
        lines.drop(1).forEach { line ->
            val parts = line.split(",")
            val data = mapper(parts)
            result.add(data)
        }
    }

    println("Table $tableName loaded.")
    return result
}
