package umc.study.umc_7th.data;

import umc.study.umc_7th.User
import umc.study.umc_7th.data.local.TokenPreference;
import umc.study.umc_7th.data.network.Server;
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val server: Server,
    private val tokenPreference: TokenPreference,
) {
    suspend fun getMyProfile(): User? {
        val user = try {
            val token = tokenPreference.accessToken ?: return null
            server.getMyProfile(token)
        } catch (_: Exception) {
            val refreshToken = tokenPreference.refreshToken ?: return null
            val auth = server.refresh(refreshToken)
            tokenPreference.accessToken = auth.accessToken
            tokenPreference.refreshToken = auth.refreshToken
            server.getMyProfile(auth.accessToken)
        }
        return user
    }

    suspend fun login(email: String, password: String) {
        val auth = server.login(email, password)
        tokenPreference.accessToken = auth.accessToken
        tokenPreference.refreshToken = auth.refreshToken
    }

    fun logout() {
        tokenPreference.clearTokens()
    }
}
