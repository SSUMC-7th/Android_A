package umc.study.umc_7th

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
)

@Serializable
data class Auth(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
)