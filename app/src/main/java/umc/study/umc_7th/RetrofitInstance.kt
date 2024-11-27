package umc.study.umc_7th

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(ApiRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val authApi = retrofit.create(AuthApi::class.java)
        val songApi = retrofit.create(SongApi::class.java)
    }
}