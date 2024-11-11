package umc.study.umc_7th

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import umc.study.umc_7th.data.source.local.AppDatabase

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt가 애플리케이션 전역에서 의존성 주입을 가능하게 합니다.
        // 여기서 앱 전역의 초기화 작업을 수행할 수 있습니다.

        // 예시: 앱의 초기화 작업 (필요한 경우)
        // 예시: 네트워크 초기화
        // NetworkHelper.initialize()

        AppDatabase.getInstance(this)

        // 추가적으로 필요한 전역 설정이나 초기화 작업을 여기에 넣습니다.
    }
}