package umc.study.umc_7th

import androidx.compose.ui.graphics.ImageBitmap
import java.time.LocalDate
import java.util.Date
import java.util.Dictionary

data class Content(
    val title: String,
    val author : String,
    val image: ImageBitmap?= null,
    val length: Int,
)

data class Album (
    val albumTitle : String,
    val date : LocalDate,
    val author : String,
    val albumImage: ImageBitmap,
    val trackList : List<Content>,
    val titleTrackList : List<Content>
)