package umc.study.umc_7th

import android.os.Parcel
import androidx.compose.ui.graphics.ImageBitmap
import java.time.LocalDate
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class Content(
    val title: String,
    val author : String,
    val image: ImageBitmap?= null,
    val length: Int,
)


data class Album (
    val albumTitle : String, // iu 5th Album 'LILAC',
    val date : LocalDate,
    val author : String,
    val albumImage: ImageBitmap,
    val trackList : List<String> ,
    val titleTrackList : List<String>
)