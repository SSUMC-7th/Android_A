package umc.study.umc_7th.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.study.umc_7th.data.model.UserLoginRequest
import umc.study.umc_7th.data.model.UserLoginResponse
import umc.study.umc_7th.data.source.remote.Service
import umc.study.umc_7th.ui.composables.NetworkViewInterface
import umc.study.umc_7th.utils.SharedPreferencesHelper.saveUserInfo
import umc.study.umc_7th.utils.SharedPreferencesHelper.saveUserToken

class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, callback: NetworkViewInterface, context: Context) {
        viewModelScope.launch {
            callback.onLoading()
            val call = Service.apiService.loginUser(UserLoginRequest(email, password))
            call.enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.result?.let { result ->
                            if (response.body()?.isSuccess == true) {
                                val accessToken = result.accessToken
                                val memberId = result.memberId
                                // 로그인 성공 시 토큰 및 유저 ID를 SharedPreferences에 저장
                                saveUserInfo(context, email, memberId)
                                saveUserToken(context, accessToken)
                                callback.onSuccess(response.body()!!)
                            } else {
                                callback.onError("Unexpected response: Missing memberId or accessToken")
                            }
                        } ?: run {
                            callback.onError("Empty response body")
                        }
                    } else {
                        callback.onError("Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    callback.onError("Exception: ${t.message}")
                }
            })
        }
    }

    fun kakao_login(context: Context) {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

}
