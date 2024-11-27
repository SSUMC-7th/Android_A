package umc.study.umc_7th

import retrofit2.Call
import retrofit2.http.GET

interface SongApi {
    @GET("/song")
    fun getSongs(): Call<SongResponse>
}