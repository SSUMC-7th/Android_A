package umc.study.umc_7th.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.study.umc_7th.data.model.UserRequest
import umc.study.umc_7th.data.model.UserResponse
import umc.study.umc_7th.data.source.remote.Service
import umc.study.umc_7th.ui.composables.NetworkViewInterface
import umc.study.umc_7th.utils.SharedPreferencesHelper.saveUserInfo

class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, callback: NetworkViewInterface, context: Context) {
        viewModelScope.launch {
            callback.onLoading()
            val call = Service.apiService.loginUser(UserRequest(email, password))
            call.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.isSuccess) {
                                val accessToken = it.result?.accessToken
                                val memberId = it.result?.memberId
                                if (accessToken != null && memberId != null) {
                                    // 로그인 성공 시 토큰 및 유저 ID를 SharedPreferences에 저장
                                    saveUserInfo(context, email, accessToken)
                                    callback.onSuccess(it)
                                } else {
                                    callback.onError("Unexpected response: Missing memberId or accessToken")
                                }
                            } else {
                                callback.onError(it.message)
                            }
                        } ?: run {
                            callback.onError("Empty response body")
                        }
                    } else {
                        callback.onError("Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    callback.onError("Exception: ${t.message}")
                }
            })
        }
    }

}
