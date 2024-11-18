package umc.study.umc_7th.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.local.TokenPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenPreferenceModule {
    @Provides
    @Singleton
    fun provideTokenPreference(
        @ApplicationContext context: Context
    ): TokenPreference {
        return TokenPreference(context)
    }
}