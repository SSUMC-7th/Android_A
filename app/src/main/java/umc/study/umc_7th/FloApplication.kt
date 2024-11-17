package umc.study.umc_7th

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import umc.study.umc_7th.service.ServiceHandler
import javax.inject.Inject

@HiltAndroidApp
class FloApplication: Application() {
    @Inject lateinit var serviceHandler: ServiceHandler

    override fun onCreate() {
        super.onCreate()
        serviceHandler.bindService()
    }

    override fun onTerminate() {
        super.onTerminate()
        serviceHandler.unbindService()
    }
}