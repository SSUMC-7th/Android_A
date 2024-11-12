package umc.study.umc_7th.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Song::class, Album::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // `Foreign Key`를 제거하는 SQL 쿼리 실행
                db.execSQL("PRAGMA foreign_keys=OFF;")

                // 새로 테이블을 생성하고 기존 데이터 이전
                db.execSQL("""
                CREATE TABLE songTable_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                singer TEXT NOT NULL,
                second INTEGER NOT NULL,
                playTime INTEGER NOT NULL,
                isPlaying INTEGER NOT NULL,
                music TEXT NOT NULL,
                coverImg INTEGER,
                isLike INTEGER NOT NULL,
                albumIdx INTEGER NOT NULL)
                """)

                // 기존 데이터를 새로운 테이블로 복사
                db.execSQL("""
                INSERT INTO songTable_new (id, title, singer, second, playTime, isPlaying, music, coverImg, isLike, albumIdx)
                SELECT id, title, singer, second, playTime, isPlaying, music, coverImg, isLike, albumIdx
                FROM songTable
                """)

                // 기존 테이블 삭제
                db.execSQL("DROP TABLE songTable")

                // 새 테이블을 기존 테이블 이름으로 변경
                db.execSQL("ALTER TABLE songTable_new RENAME TO songTable")

                // Foreign key 기능 다시 켬
                db.execSQL("PRAGMA foreign_keys=ON;")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "music_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Entity(tableName = "songTable")
data class Song(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String = "",
    var singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false,
    var albumIdx: Int = 0
)

@Entity(tableName = "albumTable")
data class Album(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String = "",
    var singer: String = "",
    var coverImg: Int = 0,
)
