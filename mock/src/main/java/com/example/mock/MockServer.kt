package com.example.mock

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
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

                val musics = if (id != null)
                    MockDatabase.getMusicById(id)?.let { listOf(it) } ?: emptyList()
                else if (albumId != null)
                    MockDatabase.getMusicByAlbum(albumId)
                else {
                    call.respondText("Invalid parameters", status = HttpStatusCode.BadRequest)
                    return@get
                }

                call.respond(musics.map { it.toResponse() })
            }

            get("/music/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10

                if (size !in 1..30) {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val musics = MockDatabase.getRandomMusicList(size)
                call.respond(musics.map { it.toResponse() })
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

                call.respond(album.toResponse())
            }

            get("/podcast/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1
                if (size !in 1..30) {
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

                if (size !in 1..30) {
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
                if (id == null || rangeHeader == null) {
                    call.respondText("Invalid request", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val file = MockStorage.getSoundFile(id)
                if (file == null) {
                    call.respondText("Sound not found", status = HttpStatusCode.NotFound)
                    return@get
                }

                val ranges = rangeHeader.removePrefix("bytes=").split("-")
                MockStream.streamFile(
                    call = call,
                    file = file,
                    start = ranges[0].toLong(),
                    end = if (ranges.size > 1) ranges[1].toLong() else null,
                )
            }
        }
    }.start(wait = true)
}