package umc.study.umc_7th

import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val title: String,
    val author : String,
    val image: Int?= null,
    val length: Int,
) : Parcelable


data class Album (
    val albumTitle : String, // iu 5th Album 'LILAC',
    val date : LocalDate,
    val author : String,
    val albumImage: ImageBitmap,
    val trackList : List<String> ,
    val titleTrackList : List<String>
)


@Parcelize
data class BannerData(
    val title1: String,
    val date: LocalDate,
    val contentList: List<Content>,
    val backgroundImage: Int,
    val textColor : Long
) : Parcelable

@RequiresApi(Build.VERSION_CODES.P)
val bannerDataList = listOf(
    BannerData(
        title1 = "포근하게 덮어주는 꿈의 목소리2",
        date = LocalDate.parse("2024-03-06"),
        contentList = List(16){
            Content(
                title = "Butter",
                author = "BTS",
                image = R.drawable.img_album_exp,
                length = 245)
            Content(
                title = "Next Level",
                author = "aespa",
                image = R.drawable.img_album_exp3,
                length = 199)
        },
        backgroundImage = R.drawable.img_default_4_x_1,
        textColor = 0xFFFFFFFF

    ),
    BannerData(
        title1 = "포근하게 덮어주는 꿈의 목소리 3",
        date = LocalDate.parse("2024-03-06"),
        contentList = List(15) {
            Content(
                title = "작은 것들을 위한 시",
                author = "BTS",
                image = R.drawable.img_album_exp4,
                length = 220,
            )
        },
        backgroundImage = R.drawable.img_default_4_x_1,
        textColor = 0xFFFFFFFF
    ),
    BannerData(
        title1 = "포근하게 덮어주는 꿈의 목소리 4",
        date = LocalDate.parse("2024-03-06"),
        contentList = List(15){
            Content(
                title = "해야",
                author = "IVE(아이브)",
                image = R.drawable.img_album_heya,
                length= 200)},
        backgroundImage = R.drawable.img_default_4_x_1,
        textColor = 0xFFFFFFFF
    )
)