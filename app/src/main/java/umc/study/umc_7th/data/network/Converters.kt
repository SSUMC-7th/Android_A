package umc.study.umc_7th.data.network

import umc.study.umc_7th.Auth
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.User
import umc.study.umc_7th.VideoContent

fun MusicContentResponse.toMusicContent(): MusicContent = MusicContent(
    id = this.id,
    title = this.title,
    author = this.author,
    imageId = this.imageId,
    length = this.length,
    albumId = this.albumId,
)

fun PodcastContentResponse.toPodcastContent(): PodcastContent = PodcastContent(
    id = this.id,
    title = this.title,
    author = this.author,
    imageId = this.imageId,
    length = this.length,
    description = this.description,
)

fun VideoContentResponse.toVideoContent(): VideoContent = VideoContent(
    id = this.id,
    title = this.title,
    author = this.author,
    imageId = this.imageId,
    length = this.length,
)

fun UserResponse.toUser(): User = User(
    id = this.id,
    email = this.email,
)

fun AuthResponse.toAuth(): Auth = Auth(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    user = this.user.toUser()
)