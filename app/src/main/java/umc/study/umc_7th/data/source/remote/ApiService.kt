package umc.study.umc_7th.data.source.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import umc.study.umc_7th.data.model.UserJoinRequest
import umc.study.umc_7th.data.model.UserJoinResponse
import umc.study.umc_7th.data.model.UserLoginRequest
import umc.study.umc_7th.data.model.UserLoginResponse

interface ApiService {

    @POST("/join")
    fun registerUser(@Body user: UserJoinRequest): Call<UserJoinResponse>

    @POST("/login")
    fun loginUser(@Body user: UserLoginRequest): Call<UserLoginResponse>
}