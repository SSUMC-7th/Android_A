package umc.study.umc_7th.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.data.local.LocalDatabase
import umc.study.umc_7th.data.network.Server
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideContentRepository(
        server: Server,
        database: LocalDatabase
    ): ContentRepository {
        return ContentRepository(server, database)
    }
}