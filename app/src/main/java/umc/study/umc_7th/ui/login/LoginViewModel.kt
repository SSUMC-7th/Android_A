package umc.study.umc_7th.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.User
import umc.study.umc_7th.data.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun testLogin(
        onSuccess: suspend (User) -> Unit,
        onFailed: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = authRepository.getMyProfile()
                onSuccess(user)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: suspend (User) -> Unit,
        onRejected: suspend () -> Unit,
        onFailed: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val user = authRepository.login(email, password)
                if (user == null) onRejected()
                else onSuccess(user)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun loginWithKakao(
        onSuccess: suspend (User) -> Unit,
        onFailed: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                authRepository.loginWithKakao()
                val user = authRepository.getMyProfile()
                onSuccess(user)
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}