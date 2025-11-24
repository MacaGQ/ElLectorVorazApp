package com.example.ellectorvoraz.data.network

import android.content.Context
import com.example.ellectorvoraz.config.AppConfig.BASE_URL
import com.example.ellectorvoraz.util.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var instance: ApiService? = null

    fun getInstance(context: Context) : ApiService {

        if (instance == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val authInterceptor = okhttp3.Interceptor { chain ->
                val authToken = SharedPreferencesManager.getAuthToken(context.applicationContext)

                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                authToken?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())

            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }

        return instance!!

    }

    fun clearInstance() {
        instance = null
    }
}