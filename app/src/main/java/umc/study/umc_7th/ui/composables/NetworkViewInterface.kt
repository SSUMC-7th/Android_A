package umc.study.umc_7th.ui.composables

interface NetworkViewInterface {
    fun onLoading()
    fun onSuccess(result: Any)
    fun onError(errorMessage: String)
}