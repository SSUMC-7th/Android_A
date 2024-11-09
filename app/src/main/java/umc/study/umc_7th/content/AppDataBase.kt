package umc.study.umc_7th.content

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timer.App


@Database(entities = [Content::class], version=1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getDatabase(context : Context): AppDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "song_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}