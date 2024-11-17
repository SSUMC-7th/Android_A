package umc.study.umc_7th.di

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.ImageLoader
import umc.study.umc_7th.data.network.Server
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
    @Provides
    @Singleton
    fun provideImageLoader(server: Server): ImageLoader {
        return ImageLoader(server)
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ImageLoaderEntryPoint {
    fun getImageLoader(): ImageLoader
}