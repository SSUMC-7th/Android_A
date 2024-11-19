package umc.study.umc_7th.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.User
import umc.study.umc_7th.data.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun signUp(
        email: String,
        password: String,
        onSuccess: suspend (User) -> Unit,
        onAlreadySigned: suspend () -> Unit,
        onInvalidEmail: suspend () -> Unit,
        onInvalidPassword: suspend () -> Unit,
        onFailure: suspend () -> Unit
    ) {
        viewModelScope.launch {
            if (validateEmail(email)) onInvalidEmail()
            if (validatePassword(password)) onInvalidPassword()
            
            try {
                val user = authRepository.signUp(email, password)
                if (user == null) onAlreadySigned()
                else onSuccess(user)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (!email.contains("@")) return false
        val parts = email.split("@")
        if (parts.size != 2) return false
        if (parts[1].isEmpty()) return false
        return true
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
}