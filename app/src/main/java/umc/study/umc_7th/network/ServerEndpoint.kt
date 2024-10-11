package umc.study.umc_7th.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerEndpoint {
    @GET("/music/random")
    suspend fun getRandomMusics(@Query("size") size: Int): List<MusicContentResponse>

    @GET("/music")
    suspend fun getMusic(@Query("id") id: Long?, @Query("albumId") albumId: Long?): MusicContentResponse

    @GET("/album/{id}")
    suspend fun getAlbum(@Path("id") id: Long): AlbumResponse

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
    suspend fun getImage(@Path("id") id: Long): ByteArray
}