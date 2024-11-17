package umc.study.umc_7th.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.content.AppDataBase

class UserViewModel(application : Application, private var userRepository: UserRepository) : AndroidViewModel(application) {
    init{
        val userDao = AppDataBase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun registerUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isRegistered = userRepository.registerUser(email, password)
            onResult(isRegistered)
        }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isLoggedIn = userRepository.loginUser(email, password)
            onResult(isLoggedIn)
        }
    }


}