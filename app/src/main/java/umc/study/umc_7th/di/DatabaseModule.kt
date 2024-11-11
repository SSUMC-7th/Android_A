package umc.study.umc_7th.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.repository.AlbumRepository
import umc.study.umc_7th.data.repository.SongRepository
import umc.study.umc_7th.data.source.local.AlbumDao
import umc.study.umc_7th.data.source.local.AppDatabase
import umc.study.umc_7th.data.source.local.SongDao

@Module
@InstallIn(SingletonComponent::class) // Singleton 범위로 설정
object DatabaseModule {

    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    fun provideSongDao(appDatabase: AppDatabase): SongDao {
        return appDatabase.songDao()
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }
}