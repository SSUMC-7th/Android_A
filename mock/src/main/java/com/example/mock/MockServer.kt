package com.example.mock

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { json() }

        routing {
            get("/music") {
                val id = call.request.queryParameters["id"]?.toLongOrNull()
                val albumId = call.request.queryParameters["albumId"]?.toLongOrNull()

                val musics =
                    if (id != null) MockDatabase.getMusicById(id)?.let { listOf(it) } ?: emptyList()
                    else if (albumId != null) MockDatabase.getMusicsByAlbum(albumId)
                    else {
                        call.respondText("Invalid parameters", status = HttpStatusCode.BadRequest)
                        return@get
                    }

                call.respond(musics.map { it.toResponse() })
            }

            get("/music/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10

                if (size !in 1..100) {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val musics = MockDatabase.getRandomMusicList(size)
                call.respond(musics.map { it.toResponse() })
            }

            get("/album/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1
                if (size !in 1..100) {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val albums = MockDatabase.getRandomAlbumList(size)
                call.respond(albums.map { it.toResponse() })
            }

            get("/album/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val album = MockDatabase.getAlbumById(id)
                if (album == null) {
                    call.respondText("Album not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(album.toContentResponse())
            }

            get("/podcast/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1
                if (size !in 1..100) {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                }

                val podcasts = MockDatabase.getRandomPodcastList(size)
                call.respond(podcasts.map { it.toResponse() })
            }

            get("/podcast/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val podcast = MockDatabase.getPodcastById(id)
                if (podcast == null) {
                    call.respondText("Podcast not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(podcast.toResponse())
            }

            get("/video/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1

                if (size !in 1..100) {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val videos = MockDatabase.getRandomVideoList(size)
                call.respond(videos.map { it.toResponse() })
            }

            get("/video/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val video = MockDatabase.getVideoById(id)
                if (video == null) {
                    call.respondText("Video not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(video.toResponse())

            }

            get("/author/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val author = MockDatabase.getAuthorById(id)
                if (author == null) {
                    call.respondText("Author not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(author.toResponse())
            }

            get("/image/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val file = MockStorage.getImageFile(id)
                if (file == null) {
                    call.respondText("Image not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                call.respondFile(file = file)
            }

            get("/stream/sound/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                val rangeHeader = call.request.headers["Range"]
                if (id == null) {
                    call.respondText("Invalid request", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val file = MockStorage.getSoundFile(id)
                if (file == null) {
                    call.respondText("Sound not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                if (rangeHeader == null) {
                    call.respondFile(file = file)
                    return@get
                }

                val ranges = rangeHeader.removePrefix("bytes=").split("-")
                MockStream.streamFile(
                    call = call,
                    file = file,
                    start = ranges[0].toLong(),
                    end = if (ranges.size > 1) ranges[1].toLongOrNull() else null,
                )
            }

            post("/auth/signup") {
                val request = call.receive<SignUpRequest>()

                // 이메일 형식 검증
                if (!request.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf("error" to "Invalid email format")
                    )
                    return@post
                }

                // 비밀번호 길이 검증
                if (request.password.length < 6) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Password must be at least 6 characters")
                    )
                    return@post
                }

                val user = MockDatabase.createUser(
                    email = request.email, password = request.password
                )

                if (user == null) {
                    call.respond(HttpStatusCode.Conflict, mapOf("error" to "Email already exists"))
                    return@post
                }

                val tokenPair = JwtConfig.createTokenPair(user)
                call.respond(
                    AuthResponse(
                        accessToken = tokenPair.accessToken,
                        refreshToken = tokenPair.refreshToken,
                        user = user.toResponse()
                    )
                )
            }

            post("/auth/login") {
                val request = call.receive<LoginRequest>()
                val user = MockDatabase.getUserByEmail(request.email)

                if (user == null || user.password != request.password) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials")
                    )
                    return@post
                }

                val tokenPair = JwtConfig.createTokenPair(user)
                call.respond(
                    AuthResponse(
                        accessToken = tokenPair.accessToken,
                        refreshToken = tokenPair.refreshToken,
                        user = user.toResponse()
                    )
                )
            }

            post("/auth/refresh") {
                val refreshToken =
                    call.request.headers["Refresh-Token"] ?: return@post call.respond(
                        HttpStatusCode.BadRequest, mapOf("error" to "Missing refresh token")
                    )

                try {
                    val decodedJWT = JwtConfig.verifyRefreshToken(refreshToken)
                    val userId = decodedJWT.getClaim("userId").asLong()
                    val user = MockDatabase.getUserById(userId)
                        ?: throw IllegalStateException("User not found")

                    val tokenPair = JwtConfig.createTokenPair(user)
                    call.respond(
                        AuthResponse(
                            accessToken = tokenPair.accessToken,
                            refreshToken = tokenPair.refreshToken,
                            user = user.toResponse()
                        )
                    )
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Invalid refresh token")
                    )
                }
            }

            get("/auth/me") {
                val authHeader = call.request.headers["Authorization"]
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Missing or invalid token")
                    )
                    return@get
                }

                try {
                    val token = authHeader.substring(7)
                    val decodedJWT = JwtConfig.verifyAccessToken(token)
                    val userId = decodedJWT.getClaim("userId").asLong()
                    val user = MockDatabase.getUserById(userId)
                        ?: throw IllegalStateException("User not found")

                    call.respond(user.toResponse())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid token"))
                }
            }

            get("/auth/like/all") {
                val authHeader = call.request.headers["Authorization"]
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Missing or invalid token")
                    )
                    return@get
                }

                val userId = call.request.queryParameters["userId"]?.toLongOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Missing parameters"))
                    return@get
                }

                val likes = MockDatabase.getAllLikes(userId)
                call.respond(likes.map { it.toResponse() })
            }

            get("/auth/like") {
                val authHeader = call.request.headers["Authorization"]
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Missing or invalid token")
                    )
                    return@get
                }

                val userId = call.request.queryParameters["userId"]?.toLongOrNull()
                val contentId = call.request.queryParameters["contentId"]?.toLongOrNull()

                if (userId == null || contentId == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Missing parameters"))
                    return@get
                }

                val like = MockDatabase.getLike(userId, contentId)

                if (like == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Like not found"))
                    return@get
                }

                call.respond(like.toResponse())
            }

            post("/auth/like") {
                val authHeader = call.request.headers["Authorization"]
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    call.respond(
                        HttpStatusCode.Unauthorized, mapOf("error" to "Missing or invalid token")
                    )
                    return@post
                }

                val userId = call.request.queryParameters["userId"]?.toLongOrNull()
                val contentId = call.request.queryParameters["contentId"]?.toLongOrNull()
                val isLiked = call.request.queryParameters["setTo"]?.toBooleanStrictOrNull()

                if (userId == null || contentId == null || isLiked == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Missing parameters"))
                    return@post
                }

                if (isLiked) MockDatabase.addLike(userId, contentId)
                else MockDatabase.cancelLike(userId, contentId)
                call.respond(HttpStatusCode.OK)
            }
        }
    }.start(wait = true)
}