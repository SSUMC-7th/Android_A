package umc.study.umc_7th.data.network

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.POST

interface ServerEndpoint {
    @GET("/music/random")
    suspend fun getRandomMusics(@Query("size") size: Int): List<MusicContentResponse>

    @GET("/music")
    suspend fun getMusics(
        @Query("id") id: Long?,
        @Query("albumId") albumId: Long?
    ): List<MusicContentResponse>

    @GET("/album/random")
    suspend fun getRandomAlbums(@Query("size") size: Int): List<AlbumResponse>

    @GET("/album/{id}")
    suspend fun getAlbum(@Path("id") id: Long): AlbumContentResponse

    @GET("/podcast/random")
    suspend fun getRandomPodcasts(@Query("size") size: Int): List<PodcastContentResponse>

    @GET("/podcast/{id}")
    suspend fun getPodcast(@Path("id") id: Long): PodcastContentResponse

    @GET("/video/random")
    suspend fun getRandomVideos(@Query("size") size: Int): List<VideoContentResponse>

    @GET("/video/{id}")
    suspend fun getVideo(@Path("id") id: Long): VideoContentResponse

    @GET("/author/{id}")
    suspend fun getAuthor(@Path("id") id: Long): AuthorResponse

    @GET("/image/{id}")
    suspend fun getImage(@Path("id") id: Long): ResponseBody

    @POST("/auth/signup")
    suspend fun signup(@Body request: SignUpRequest): AuthResponse

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("/auth/refresh")
    suspend fun refresh(@Header("Refresh-Token") refreshToken: String): AuthResponse

    @GET("/auth/me")
    suspend fun getMyProfile(@Header("Authorization") token: String): UserResponse

    @GET("/auth/like/all")
    suspend fun getAllLikes(
        @Header("Authorization") token: String,
        @Query("userId") userId: Long
    ): List<LikeResponse>

    @GET("/auth/like")
    suspend fun isLiked(
        @Header("Authorization") token: String,
        @Query("userId") userId: Long,
        @Query("contentId") contentId: Long,
    ): LikeResponse

    @POST("/auth/like")
    suspend fun setLike(
        @Header("Authorization") token: String,
        @Query("userId") userId: Long,
        @Query("contentId") contentId: Long,
        @Query("setTo") isLiked: Boolean,
    ): LikeResponse
}