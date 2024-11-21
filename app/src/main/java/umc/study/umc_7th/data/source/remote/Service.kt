package umc.study.umc_7th.data.source.remote

object Service {

    val apiService: ApiService by lazy {
        NetworkManager.createService<ApiService>()
    }
}