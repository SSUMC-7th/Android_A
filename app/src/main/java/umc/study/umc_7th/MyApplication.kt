package umc.study.umc_7th

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import umc.study.umc_7th.data.source.local.AppDatabase

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}