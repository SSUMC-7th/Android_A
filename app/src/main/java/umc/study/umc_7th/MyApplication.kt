package umc.study.umc_7th

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import umc.study.umc_7th.data.source.local.AppDatabase

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        // Kakao SDK 초기화
        KakaoSdk.init(this,"{NATIVE_APP_KEY}")
    }
}