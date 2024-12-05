package umc.study.umc_7th

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FloApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_OAUTH_KEY)

        // Log.d("Flo", "hash key: ${Utility.getKeyHash(this)}")
    }
}