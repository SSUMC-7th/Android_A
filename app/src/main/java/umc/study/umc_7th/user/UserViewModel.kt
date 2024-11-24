package umc.study.umc_7th.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val onboardingService: OnboardingService =
        AuthNetworkModule.getClient().create(OnboardingService::class.java)

    // 회원가입
    fun signUp(
        name: String,
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = SignUpRequest(name, email, password)
        onboardingService.signUp(request).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    onSuccess("회원가입 성공")
                } else {
                    onError("회원가입 실패")
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                onError("서버 오류: ${t.message}")
            }
        })
    }

    // 로그인
    fun login(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = LoginRequest(email, password)
        onboardingService.login(request).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val token = response.body()?.result?.memberId.toString()
                    AuthNetworkModule.setAccessToken(token) // 토큰 저장
                    onSuccess("로그인 성공")
                } else {
                    onError("로그인 실패")
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                onError("서버 오류: ${t.message}")
            }
        })
    }
}
