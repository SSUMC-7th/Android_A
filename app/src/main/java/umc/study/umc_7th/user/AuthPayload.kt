package umc.study.umc_7th.user


data class JoinResponse(
    val isSuccess : Boolean,
    val code : String,
    val messsage : String,
    val result : TimeStamp
)

data class TimeStamp(
    val memberId: Int,
    val createAt:String,
    val updatedAt: String
)

data class SignUpRequest(
    val name : String,
    val email : String,
    val password : String
)

data class LoginRequest(
    val email: String,
    val password: String
)