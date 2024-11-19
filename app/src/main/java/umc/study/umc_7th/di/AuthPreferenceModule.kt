package umc.study.umc_7th.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.network.AuthPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthPreferenceModule {
    @Provides
    @Singleton
    fun provideAuthPreference(@ApplicationContext context: Context): AuthPreference {
        return AuthPreference(context)
    }
}