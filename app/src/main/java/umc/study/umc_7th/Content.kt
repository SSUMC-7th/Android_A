package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import java.io.Serializable
import java.time.LocalDate

sealed interface Content: Serializable {
    val id: Long
    val title: String
    val author: String
    val imageId: Long
    val image: ImageBitmap?
    val length: Int  // 단위: 초
}

data class Album(
    val id: Long,
    val title: String,
    val author: String,
    val imageId: Long,
    val image: ImageBitmap? = null,
    val type: String,
    val genre: String,
    val releasedDate: LocalDate,
    val contentList: List<MusicContent>,
): Serializable

data class MusicContent(
    override val id: Long,
    override val title: String,
    override val author: String,
    override val imageId: Long,
    override val image: ImageBitmap? = null,
    override val length: Int,
    val albumId: Long,
    val label: String? = null,
): Content, Serializable

data class PodcastContent(
    override val id: Long,
    override val title: String,
    override val author: String,
    override val imageId: Long,
    override val image: ImageBitmap? = null,
    override val length: Int,
    val description: String?,
): Content, Serializable

data class VideoContent(
    override val id: Long,
    override val title: String,
    override val author: String,
    override val imageId: Long,
    override val image: ImageBitmap? = null,
    override val length: Int,
): Content, Serializable


val previewAlbum @RequiresApi(Build.VERSION_CODES.O) @Composable get() = Album(
    id = 100,
    title = "IU 5th Album \"LILAC\"",
    author = "아이유(IU)",
    imageId = 100,
    image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
    type = "정규",
    genre = "댄스 팝",
    releasedDate = LocalDate.of(2023, 1, 1),
    contentList = previewMusicContentList,
)

val previewMusicContentList @Composable get() = listOf(
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
    MusicContent(
        id = 100,
        title = title,
        author = "아이유(IU)",
        imageId = 101,
        image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
        length = 210,
        albumId = 100,
        label = if (index == 0 || index == 2) "TITLE" else null
    )
}

val previewPodcastContentList @Composable get() = List(15) {
    PodcastContent(
        id = 100,
        title = "김시선의 귀책사유 FLO X 윌라",
        author = "김시선",
        imageId = 100,
        image = ImageBitmap.imageResource(id = R.drawable.img_potcast_exp),
        length = 200,
        description = null,
    )
}

val previewVideoContentList @Composable get() = List(15) {
    VideoContent(
        id = 100,
        title = "제목",
        author = "지은이",
        imageId = 100,
        image = ImageBitmap.imageResource(id = R.drawable.img_video_exp),
        length = 200,
    )
}