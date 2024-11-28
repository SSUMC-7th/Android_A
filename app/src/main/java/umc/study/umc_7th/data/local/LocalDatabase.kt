package umc.study.umc_7th.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.local.dao.SavedAlbumDao
import umc.study.umc_7th.data.local.dao.SavedMusicDao
import umc.study.umc_7th.data.local.entity.SavedAlbumEntity
import umc.study.umc_7th.data.local.entity.SavedMusicEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(
    entities = [
        SavedMusicEntity::class,
        SavedAlbumEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getSavedMusicDao(): SavedMusicDao
    abstract fun getSavedAlbumDao(): SavedAlbumDao

    val savedMusicFlow = MutableStateFlow<List<MusicContent>>(emptyList())
    val savedAlbumFlow = MutableStateFlow<List<AlbumContent>>(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                getSavedMusicDao().getFlow()
                    .collect { flow -> savedMusicFlow.value = flow.map { it.toContent() } }
            }
            launch {
                getSavedAlbumDao().getFlow()
                    .collect { flow -> savedAlbumFlow.value = flow.map { it.toContent() } }
            }
        }
    }

    suspend fun getSavedMusic(id: Long): MusicContent? {
        return getSavedMusicDao().get(id).firstOrNull()?.toContent()
    }

    suspend fun addSavedMusic(vararg musics: MusicContent) {
        getSavedMusicDao().insert(*musics.map { SavedMusicEntity.fromContent(it) }.toTypedArray())
    }

    suspend fun deleteSavedMusic(music: MusicContent) {
        getSavedMusicDao().delete(SavedMusicEntity.fromContent(music))
    }

    suspend fun getSavedAlbum(id: Long): AlbumContent? {
        return getSavedAlbumDao().get(id)?.toContent()
    }

    suspend fun addSavedAlbum(album: AlbumContent) {
        getSavedAlbumDao().insert(SavedAlbumEntity.fromContent(album))
    }

    suspend fun deleteSavedAlbum(album: AlbumContent) {
        getSavedAlbumDao().delete(SavedAlbumEntity.fromContent(album))
    }
}

class Converter {
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?) = date?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun toLocalDateTime(epochSecond: Long?) = epochSecond?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?) = date?.toEpochDay()

    @TypeConverter
    fun toLocalDate(epochDay: Long?) = epochDay?.let {
        LocalDate.ofEpochDay(it)
    }
}
