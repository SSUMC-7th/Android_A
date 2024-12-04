package umc.study.umc_7th.user

import android.graphics.Paint.Join
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface OnboardingService{
    @POST("/join")
    fun signUp(@Body request: SignUpRequest): Call<JoinResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<JoinResponse>
}

object AuthNetworkModule{
    private const val BASE_URL = "http://3.35.121.185/"

    private var retrofit: Retrofit? = null

    private var accessToken: String? = null

    fun setAccessToken(token : String){
        accessToken = token
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val original= chain.request()
                val requestBuilder = original.newBuilder()
                accessToken?.let{
                    requestBuilder.header("Authorization", "Bearer $it")
                }
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

    }

    fun getClient(): Retrofit{
        return retrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().also{ retrofit = it}

    }
}