package umc.study.umc_7th.content

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timer.App


@Database(entities = [Content::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "song_database"
                )
                    .fallbackToDestructiveMigration() // 데이터베이스 초기화 옵션 추가
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
