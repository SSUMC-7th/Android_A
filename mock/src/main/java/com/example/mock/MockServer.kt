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
                else
                    null
                if (musics != null)
                    call.respond(musics.map { it.toResponse() })
                else
                    call.respondText("Invalid parameters", status = HttpStatusCode.BadRequest)
            }

            get("/music/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10
                if (size in 1..30) {
                    val musics = MockDatabase.getRandomMusicList(size)
                    call.respond(musics.map { it.toResponse() })
                } else {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                }
            }

            get("/album/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val album = MockDatabase.getAlbumById(id)
                    if (album != null) {
                        call.respond(album.toResponse())
                    } else {
                        call.respondText("Album not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/podcast/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1
                if (size in 1..30) {
                    val podcasts = MockDatabase.getRandomPodcastList(size)
                    call.respond(podcasts.map { it.toResponse() })
                } else {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                }
            }

            get("/podcast/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val podcast = MockDatabase.getPodcastById(id)
                    if (podcast != null) {
                        call.respond(podcast.toResponse())
                    } else {
                        call.respondText("Podcast not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/video/random") {
                val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 1
                if (size in 1..30) {
                    val videos = MockDatabase.getRandomVideoList(size)
                    call.respond(videos.map { it.toResponse() })
                } else {
                    call.respondText("Invalid size", status = HttpStatusCode.BadRequest)
                }
            }

            get("/video/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val video = MockDatabase.getVideoById(id)
                    if (video != null) {
                        call.respond(video.toResponse())
                    } else {
                        call.respondText("Video not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/author/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val author = MockDatabase.getAuthorById(id)
                    if (author != null) {
                        call.respond(author.toResponse())
                    } else {
                        call.respondText("Author not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/image/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val file = MockImageStorage.getImageFile(id)
                    if (file != null)
                        call.respondFile(file = file)
                    else
                        call.respondText("Image not found", status = HttpStatusCode.NotFound)
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }.start(wait = true)
}