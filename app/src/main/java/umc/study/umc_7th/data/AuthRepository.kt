package umc.study.umc_7th.data;

import umc.study.umc_7th.data.network.KakaoOAuthServer
import umc.study.umc_7th.data.network.Server
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val server: Server,
    private val kakaoOAuthServer: KakaoOAuthServer,
) {
    suspend fun signUp(email: String, password: String) = server.signUp(email, password)
    suspend fun login(email: String, password: String) = server.login(email, password)
    suspend fun loginWithKakao() {
        val token = kakaoOAuthServer.login() ?: throw Exception("카카오 로그인 실패")
        val email = "${token.substring(0, 20)}@kakao.com"
        val password = token.substring(0, 20)
        try {
            server.signUp(email, password)
        }
        catch (e: Exception) {
            //empty
        }
        finally {
            server.login(email, password)
        }
    }
    fun logout() = server.logout()
    suspend fun getMyProfile() = server.getMyProfile()
}
