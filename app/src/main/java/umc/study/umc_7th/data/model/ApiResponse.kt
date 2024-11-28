package umc.study.umc_7th.data.model

data class UserJoinRequest(
    var name: String,
    var email: String,
    var password: String
)

data class UserJoinResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result?
) {
    data class Result(
        val memberId: Int,
        val createdAt: String,
        val updatedAt: String
    )
}

data class UserLoginRequest(
    var email: String,
    var password: String
)

data class UserLoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result?
) {
    data class Result(
        val memberId: Int,
        val accessToken: String
    )
}