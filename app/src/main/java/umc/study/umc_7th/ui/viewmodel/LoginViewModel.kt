package umc.study.umc_7th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.data.model.UserRequest
import umc.study.umc_7th.data.source.remote.Service
import umc.study.umc_7th.ui.composables.NetworkViewInterface

class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, callback: NetworkViewInterface) {
        viewModelScope.launch {
            callback.onLoading()
            val user = UserRequest(email, password)
            val call = Service.apiService.loginUser(user)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: run {
                        callback.onError("Empty response body")
                    }
                } else {
                    callback.onError("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                callback.onError("Exception: ${e.message}")
            }
        }
    }
}
