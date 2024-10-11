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
            get("/music/random") {
                val musics = MockDatabase.getRandomMusicList(10)
                call.respond(musics)
            }

            get("/music/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val music = MockDatabase.getMusicById(id)
                    if (music != null) {
                        call.respond(music)
                    } else {
                        call.respondText("Music not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/album/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val album = MockDatabase.getAlbumById(id)
                    if (album != null) {
                        call.respond(album)
                    } else {
                        call.respondText("Album not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/podcast/random") {
                val podcasts = MockDatabase.getRandomPodcastList(1)
                call.respond(podcasts)
            }

            get("/podcast/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val podcast = MockDatabase.getPodcastById(id)
                    if (podcast != null) {
                        call.respond(podcast)
                    } else {
                        call.respondText("Podcast not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/video/random") {
                val videos = MockDatabase.getRandomVideoList(1)
                call.respond(videos)
            }

            get("/video/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    val video = MockDatabase.getVideoById(id)
                    if (video != null) {
                        call.respond(video)
                    } else {
                        call.respondText("Video not found", status = HttpStatusCode.NotFound)
                    }
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }

            get("/image/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    MockImageStorage.getImageInputStream(id)?.use { inputStream ->
                        call.respondOutputStream(
                            status = HttpStatusCode.OK,
                            contentType = ContentType.Image.Any
                        ) {
                            inputStream.copyTo(this)
                        }
                    } ?: call.respondText("Image not found", status = HttpStatusCode.NotFound)
                } else {
                    call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }.start(wait = true)
}