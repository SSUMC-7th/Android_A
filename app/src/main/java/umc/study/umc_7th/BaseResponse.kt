package umc.study.umc_7th

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : Result?
)

data class Result (
    @SerializedName("userIdx") var userIdx : Int,
    @SerializedName("jwt") var jwt : String
)
