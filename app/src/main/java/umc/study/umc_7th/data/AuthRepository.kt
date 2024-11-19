package umc.study.umc_7th.data;

import android.util.Log
import umc.study.umc_7th.data.network.Server
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val server: Server,
) {
    suspend fun signUp(email: String, password: String) = server.signUp(email, password)
    suspend fun login(email: String, password: String) = server.login(email, password)
    fun logout() = server.logout()
    suspend fun getMyProfile() = server.getMyProfile()
}
