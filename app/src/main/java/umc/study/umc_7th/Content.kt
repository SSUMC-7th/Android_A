package umc.study.umc_7th

import android.os.Build
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import java.io.Serializable
import java.time.LocalDate

sealed class Content(
    open val title: String,
    open val author: String,
    open val imageId: Int,
    open val length: Int,  // 단위: 초
): Serializable {
    val imageBitmap: ImageBitmap @Composable get() = ImageLoader.getImageBitmap(id = imageId)
}

data class Album(
    val title: String,
    val author: String,
    val imageId: Int,
    val type: String,
    val genre: String,
    val releasedDate: LocalDate,
    val contentList: MutableList<Pair<MusicContent, String?>>,
): Serializable {
    val imageBitmap: ImageBitmap @Composable get() = ImageLoader.getImageBitmap(id = imageId)
}

data class MusicContent(
    override val title: String,
    override val author: String,
    override val imageId: Int,
    override val length: Int,  // 단위: 초
    val album: Album,
): Content(
    title = title,
    author = author,
    imageId = imageId,
    length = length,
), Serializable

data class PodcastContent(
    override val title: String,
    override val author: String,
    override val imageId: Int,
    override val length: Int,  // 단위: 초
): Content(
    title = title,
    author = author,
    imageId = imageId,
    length = length,
), Serializable

data class VideoContent(
    override val title: String,
    override val author: String,
    override val imageId: Int,
    override val length: Int,  // 단위: 초
): Content(
    title = title,
    author = author,
    imageId = imageId,
    length = length,
), Serializable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getTestMusicContentList(
    @IntRange(from = 1, to = 4) number: Int
): List<MusicContent> = when (number) {
    1 -> Album(
        title = "IU 5th Album \"LILAC\"",
        author = "아이유(IU)",
        imageId = R.drawable.img_album_exp2,
        type = "정규",
        genre = "댄스 팝",
        releasedDate = LocalDate.parse("2021-03-25"),
        contentList = mutableListOf()
    ).run {
        listOf(
            "라일락",
            "Flu",
            "Coin",
            "봄 안녕 봄",
            "Celebrity",
            "돌림노래 (feat. DEAN)",
            "빈 컵 (Empty Cup)",
            "아이와 나의 바다",
            "어푸 (Ah puh)",
            "에필로그",
        ).mapIndexed { index, title ->
            val content = MusicContent(
                title = title,
                author = "아이유(IU)",
                imageId = R.drawable.img_album_exp2,
                length = 210,
                album = this,
            )
            this.contentList.add(content to if (index == 0 || index == 2) "TITLE" else null)
            content
        }
    }

    2 -> Album(
        title = "Butter (feat. Megan Thee Stallion)",
        author = "BTS",
        imageId = R.drawable.img_album_exp,
        type = "싱글",
        genre = "댄스 팝",
        releasedDate = LocalDate.parse("2021-05-21"),
        contentList = mutableListOf()
    ).run {
        listOf(
            "Butter",
        ).map {
            val content = MusicContent(
                title = it,
                author = "BTS",
                imageId = R.drawable.img_album_exp,
                length = 210,
                album = this,
            )
            this.contentList.add(content to "SINGLE")
            content
        }
    }

    3 -> Album(
        title = "Next Level",
        author = "aespa",
        imageId = R.drawable.img_album_exp3,
        type = "싱글",
        genre = "댄스 팝",
        releasedDate = LocalDate.parse("2021-05-17"),
        contentList = mutableListOf()
    ).run {
        listOf(
            "Next Level",
        ).map {
            val content = MusicContent(
                title = it,
                author = "aespa",
                imageId = R.drawable.img_album_exp3,
                length = 210,
                album = this,
            )
            this.contentList.add(content to "SINGLE")
            content
        }
    }

    4 -> Album(
        title = "MAP OF THE SOUL : PERSONA",
        author = "BTS",
        imageId = R.drawable.img_album_exp4,
        type = "미니",
        genre = "댄스 팝",
        releasedDate = LocalDate.parse("2019-04-12"),
        contentList = mutableListOf()
    ).run {
        listOf(
            "Intro : Persona",
            "작은 것들을 위한 시 (Boy With Luv) (Feat. Halsey)",
            "소우주 (Mikrokosmos)",
            "Make It Right",
            "HOME",
            "Jamais Vu",
            "Dionysus",
        ).mapIndexed { index, title ->
            val content = MusicContent(
                title = title,
                author = "BTS",
                imageId = R.drawable.img_album_exp4,
                length = 210,
                album = this,
            )
            this.contentList.add(content to if (index == 1) "TITLE" else null)
            content
        }
    }

    else -> throw IllegalArgumentException()
}