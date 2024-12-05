package umc.study.umc_7th.data.network

import android.content.Context
import android.util.Log
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class KakaoOAuthServer @Inject constructor(@ApplicationContext val context: Context) {
    suspend fun login(): String? = suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            when {
                error != null -> {
                    Log.e(TAG, "로그인 실패", error)
                    continuation.resume(null)
                }
                token != null -> {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    continuation.resume(token.accessToken)
                }
            }
        }
    }
}