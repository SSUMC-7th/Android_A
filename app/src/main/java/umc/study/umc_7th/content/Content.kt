package umc.study.umc_7th.content

import android.os.Build
import java.time.LocalDate
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import umc.study.umc_7th.R



@Parcelize
data class Album (
    val albumTitle : String, // iu 5th Album 'LILAC',
    val date : LocalDate,
    val author : String,
    val albumImage: Int,
    val trackList : List<String> ,
    val titleTrackList : List<String>
) : Parcelable


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
                length = 245,
                islike = false)
            Content(
                title = "Next Level",
                author = "aespa",
                image = R.drawable.img_album_exp3,
                length = 199,
                islike = false)
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
                islike = false
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
                length= 200,
                islike = false)
        },
        backgroundImage = R.drawable.img_default_4_x_1,
        textColor = 0xFFFFFFFF
    )
)


@RequiresApi(Build.VERSION_CODES.P)
val albumData = listOf(
    Album(
        albumTitle = "IU 5th Album 'LILAC'",
        date = LocalDate.parse("2023-03-27"),
        author = "IU(아이유)",
        albumImage = R.drawable.img_album_exp2,
        trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
        titleTrackList = listOf("LILAC", "Flu")
    ),

    Album(
        albumTitle = "BTS 'Map of the Soul: Persona'",
        date = LocalDate.parse("2019-04-12"),
        author = "BTS(방탄소년단)",
        albumImage = R.drawable.img_album_exp4,
        trackList = listOf("Intro: Persona", "Boy With Luv", "Mikrokosmos", "Make It Right", "Home"),
        titleTrackList = listOf("Boy With Luv")
    ),
    Album(
        albumTitle = "Next Level",
        date = LocalDate.parse("2019-04-12"),
        author = "aespa",
        albumImage = R.drawable.img_album_exp3,
        trackList = listOf("Next Level"),
        titleTrackList = listOf("Next Level")
    ),
    Album(
        albumTitle = "MOMOLAND 'BAAM'",
        date = LocalDate.parse("2018-06-26"),
        author = "MOMOLAND",
        albumImage = R.drawable.img_album_exp5,
        trackList = listOf("BAAM", "Veryvery", "Bingo Game", "Only one you", "Falling U"),
        titleTrackList = listOf("BAAM")
    ),
    Album(
        albumTitle = "Taeyeon 'Weekend'",
        date = LocalDate.parse("2021-07-06"),
        author = "Taeyeon(태연)",
        albumImage = R.drawable.img_album_exp6,
        trackList = listOf("Weekend"),
        titleTrackList = listOf("Weekend")
    ),
    Album(
        albumTitle = "aespa 'Next Level: Drama'",
        date = LocalDate.parse("2023-06-15"),
        author = "aespa(에스파)",
        albumImage = R.drawable.img_album_drama,
        trackList = listOf("Next Level", "Drama", "Black Mamba", "Forever", "Savage"),
        titleTrackList = listOf("Drama", "Next Level")
    ),
    Album(
        albumTitle = "IVE 'I've HAEYA'",
        date = LocalDate.parse("2023-08-10"),
        author = "IVE(아이브)",
        albumImage = R.drawable.img_album_heya,
        trackList = listOf("HAEYA", "After LIKE", "ELEVEN", "LOVE DIVE", "ROYAL"),
        titleTrackList = listOf("HAEYA", "After LIKE")
    )


)

