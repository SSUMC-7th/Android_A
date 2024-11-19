package umc.study.umc_7th.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.network.AuthPreference
import umc.study.umc_7th.data.network.Server
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {
    @Provides
    @Singleton
    fun provideServer(
        authPreference: AuthPreference
    ): Server {
        return Server(authPreference)
    }
}