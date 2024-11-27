package umc.study.umc_7th

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user : User) {
        RetrofitInstance.authApi.signUp(user).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("SignUp-Success", response.toString())
                val response : BaseResponse = response.body()!!
                when(response.code) {
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(response.message)
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("SignUp-Failure", t.message.toString())
            }
        })
        Log.d("SignUpActivity", "All Finished")
    }

    fun login(user : User) {
        RetrofitInstance.authApi.login(user).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("Login-Success", response.toString())
                val response : BaseResponse = response.body()!!
                when(val code = response.code) {
                    1000 -> loginView.onLoginSuccess(code, response.result!!)
                    else -> loginView.onLoginFailure(response.message)
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("Login-Failure", t.message.toString())
            }
        })
        Log.d("LoginActivity", "All Finished")
    }
}