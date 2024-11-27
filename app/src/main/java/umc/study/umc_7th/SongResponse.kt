package umc.study.umc_7th

import com.google.gson.annotations.SerializedName

data class SongResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : FloChartResult

)

data class FloChartResult(
    @SerializedName("songs") val songs: List<FloChartSongs>
)

data class FloChartSongs(
    @SerializedName("songIdx") val songIdx: Int,
    @SerializedName("title") val title: String,
    @SerializedName("singer") val singer: String,
    @SerializedName("albumIdx") val albumIdx: Int,
    @SerializedName("coverImgUrl") val coverImgUrl: String

)