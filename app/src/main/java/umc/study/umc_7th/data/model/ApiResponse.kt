package umc.study.umc_7th.data.model

data class UserRequest(
    var email: String,
    var password: String
)

data class UserResponse(
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