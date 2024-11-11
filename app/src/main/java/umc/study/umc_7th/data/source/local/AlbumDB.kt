package umc.study.umc_7th.data.source.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Database(entities = [Song::class, Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "music_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Entity(tableName = "songTable")
data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false,
    var albumidx: Int = 0
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

@Entity(tableName = "albumTable")
data class Album(
    var title: String = "",
    var singer: String = "",
    var coverImg: Int = 0,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

}
