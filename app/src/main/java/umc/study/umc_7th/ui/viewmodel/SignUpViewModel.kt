package umc.study.umc_7th.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.study.umc_7th.data.model.UserJoinRequest
import umc.study.umc_7th.data.model.UserJoinResponse
import umc.study.umc_7th.data.source.remote.Service
import umc.study.umc_7th.ui.composables.NetworkViewInterface
import umc.study.umc_7th.utils.SharedPreferencesHelper.saveUserInfo

class SignUpViewModel : ViewModel() {

    fun registerUser(name: String, email: String, password: String, callback: NetworkViewInterface, context: Context) {
        viewModelScope.launch {
            callback.onLoading()
            val user = UserJoinRequest(name, email, password)
            val call = Service.apiService.registerUser(user)
            call.enqueue(object : Callback<UserJoinResponse> {
                override fun onResponse(call: Call<UserJoinResponse>, response: Response<UserJoinResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.result?.let { result ->
                            if (response.body()?.isSuccess == true) {
                                val memberId = result.memberId
                                // 회원가입 성공 시 context를 사용해 SharedPreferences에 저장
                                saveUserInfo(context, email, memberId)
                                callback.onSuccess(response.body()!!)
                            } else {
                                callback.onError(response.body()?.message ?: "Unknown error")
                            }
                        } ?: run {
                            callback.onError("Empty response body")
                        }
                    } else {
                        callback.onError("Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserJoinResponse>, t: Throwable) {
                    callback.onError("Exception: ${t.message}")
                }
            })
        }
    }
}