package umc.study.umc_7th.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.network.KakaoOAuthServer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoOAuthServerModule {
    @Provides
    @Singleton
    fun provideKakaoOAuthServer(
        @ApplicationContext context: Context
    ): KakaoOAuthServer {
        return KakaoOAuthServer(context)
    }
}