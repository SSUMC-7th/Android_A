package umc.study.umc_7th.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.local.LocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "local_database"
        ).build()
    }

    // @Provides
    // fun provideSavedMusicDao(database: LocalDatabase): SavedMusicDao {
    //     return database.getSavedMusicDao()
    // }

    // @Provides
    // fun provideLikedContentDao(database: LocalDatabase): LikedContentDao {
    //     return database.getLikedContentDao()
    // }
}