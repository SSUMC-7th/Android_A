package umc.study.umc_7th

interface LoginView {
    fun onLoginSuccess(code : Int, result : Result)
    fun onLoginFailure(message : String)
}
