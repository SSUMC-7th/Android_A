package com.example.mock

import java.io.File
import java.time.LocalDateTime

object MockDatabase {
    private val musics = mutableListOf<MusicContent>()
    private val albums = mutableListOf<Album>()
    private val podcasts = mutableListOf<PodcastContent>()
    private val videos = mutableListOf<VideoContent>()
    private val authors = mutableListOf<Author>()
    private val users = mutableListOf<User>()

    fun getMusicById(id: Long): MusicContent? = musics.find { it.id == id }
    fun getMusicByAlbum(albumId: Long): List<MusicContent> = musics.filter { it.albumId == albumId }
    fun getAlbumById(id: Long): Album? = albums.find { it.id == id }
    fun getPodcastById(id: Long): PodcastContent? = podcasts.find { it.id == id }
    fun getVideoById(id: Long): VideoContent? = videos.find { it.id == id }
    fun getRandomMusicList(size: Int): List<MusicContent> = List(size) { musics.random() }
    fun getRandomAlbumList(size: Int): List<Album> = List(size) { albums.random() }
    fun getRandomPodcastList(size: Int): List<PodcastContent> = List(size) { podcasts.random() }
    fun getRandomVideoList(size: Int): List<VideoContent> = List(size) { videos.random() }
    fun getAuthorById(id: Long): Author? = authors.find { it.id == id }
    fun getUserById(id: Long): User? = users.find { it.id == id }
    fun getUserByEmail(email: String): User? = users.find { it.email == email }

    init {
        loadAuthors()
        loadAlbums()
        loadMusics()
        loadPodcasts()
        loadVideos()
        loadUsers()
    }

    @Synchronized
    fun createUser(email: String, password: String): User? {
        // 이메일 중복 체크
        if (getUserByEmail(email) != null) {
            return null
        }

        val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
        val createdAt = LocalDateTime.now().toString()
        
        val newUser = User(
            id = newId,
            email = email,
            password = password,
        )

        // CSV 파일에 추가
        val csvLine = "${newUser.id},${newUser.email},${newUser.password}"
        try {
            val file = File("mock/src/main/java/com/example/mock/tables/users.csv")
            file.appendText("\n$csvLine")
            users.add(newUser)
            return newUser
        } catch (e: Exception) {
            println("유저 추가 중 오류 발생: ${e.message}")
            return null
        }
    }

    private fun loadAuthors() {
        val reader =
            MockDatabase::class.java.getResourceAsStream("/tables/authors.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 3)
                authors.add(
                    Author(
                        id = parts[0].toLong(),
                        name = parts[1],
                        imageId = parts[2].takeIf { it.isNotEmpty() }?.toLong()
                    )
                )
            }
        }
    }

    private fun loadAlbums() {
        val reader =
            MockDatabase::class.java.getResourceAsStream("/tables/albums.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 6)
                albums.add(
                    Album(
                        id = parts[0].toLong(),
                        title = parts[1],
                        authorId = parts[2].toLong(),
                        imageId = parts[3].toLong(),
                        releaseDate = parts[4],
                        type = parts[5],
                        genre = parts[6]
                    )
                )
            }
        }
    }

    private fun loadMusics() {
        val reader =
            MockDatabase::class.java.getResourceAsStream("/tables/musics.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 7)
                musics.add(
                    MusicContent(id = parts[0].toLong(),
                        title = parts[1],
                        authorId = parts[2].toLong(),
                        albumId = parts[3].toLong(),
                        index = parts[4].toInt(),
                        label = parts[5].takeIf { it.isNotEmpty() },
                        lyrics = parts[6].takeIf { it.isNotEmpty() })
                )
            }
        }
    }

    private fun loadPodcasts() {
        val reader =
            MockDatabase::class.java.getResourceAsStream("/tables/podcasts.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 5)
                podcasts.add(
                    PodcastContent(
                        id = parts[0].toLong(),
                        title = parts[1],
                        authorId = parts[2].toLong(),
                        imageId = parts[3].toLong(),
                        description = parts[4]
                    )
                )
            }
        }
    }

    private fun loadVideos() {
        val reader =
            MockDatabase::class.java.getResourceAsStream("/tables/videos.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 4)
                videos.add(
                    VideoContent(
                        id = parts[0].toLong(),
                        title = parts[1],
                        authorId = parts[2].toLong(),
                        imageId = parts[3].toLong()
                    )
                )
            }
        }
    }

    private fun loadUsers() {
        val reader = MockDatabase::class.java.getResourceAsStream("/tables/users.csv")?.bufferedReader()
        reader?.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",", limit = 3)
                users.add(
                    User(
                        id = parts[0].toLong(),
                        email = parts[1],
                        password = parts[2],
                    )
                )
            }
        }
    }
}