package umc.study.umc_7th.data.source.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import umc.study.umc_7th.data.model.UserRequest
import umc.study.umc_7th.data.model.UserResponse

interface ApiService {

    @POST("users/register")
    fun registerUser(
        @Body user: UserRequest,
        @Header("Authorization") token: String? = null
    ): Call<UserResponse>

    @POST("users/login")
    fun loginUser(@Body user: UserRequest): Call<UserResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") userId: Int): Call<UserResponse>

    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<List<UserResponse>>
}