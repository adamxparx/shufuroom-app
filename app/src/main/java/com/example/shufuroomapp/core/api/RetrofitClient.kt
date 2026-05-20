package com.example.shufuroomapp.core.api

import com.example.shufuroomapp.ShufuRoomApplication
import com.example.shufuroomapp.core.utils.PrefManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://shufuroom-backend.onrender.com/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Auth Interceptor grabbing the Global Context!
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val urlPath = originalRequest.url.encodedPath

        // 1. Skip adding the token for Login and Register endpoints
        if (urlPath.contains("/api/auth/login") || urlPath.contains("/api/auth/register")) {
            return@Interceptor chain.proceed(originalRequest)
        }

        // 2. Otherwise, attach the token as normal
        val prefManager = PrefManager(ShufuRoomApplication.appContext)
        val token = prefManager.getToken()

        val requestBuilder = originalRequest.newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        // Notice the capital 'T' in Timeout!
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Back to being a simple variable!
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}