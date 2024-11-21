package umc.study.umc_7th.data.model

data class UserRequest(
    var email: String,
    var password: String
)

data class UserResponse(
    val id: Int,
    val email: String,
    val token: String
)