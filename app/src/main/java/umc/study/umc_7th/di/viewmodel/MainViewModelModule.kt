package umc.study.umc_7th.di.viewmodel

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import umc.study.umc_7th.data.AuthRepository
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.service.ServiceHandler
import umc.study.umc_7th.ui.main.MainViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainViewModelModule {
    @Provides
    @Singleton
    fun provideMainViewModel(
        contentRepository: ContentRepository,
        authRepository: AuthRepository,
        serviceHandler: ServiceHandler,
    ): MainViewModel {
        return MainViewModel(
            contentRepository = contentRepository,
            authRepository = authRepository,
            serviceHandler = serviceHandler
        )
    }
}