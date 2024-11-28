package umc.study.umc_7th.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}
