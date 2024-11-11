package umc.study.umc_7th.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import umc.study.umc_7th.data.repository.AlbumRepository
import umc.study.umc_7th.data.repository.SongRepository
import umc.study.umc_7th.data.source.local.AlbumDao
import umc.study.umc_7th.data.source.local.SongDao

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideSongRepository(songDao: SongDao): SongRepository {
        return SongRepository(songDao)
    }

    @Provides
    fun provideAlbumRepository(albumDao: AlbumDao): AlbumRepository {
        return AlbumRepository(albumDao)
    }
}