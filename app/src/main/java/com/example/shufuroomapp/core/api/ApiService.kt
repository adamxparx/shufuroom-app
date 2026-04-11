package com.example.shufuroomapp.core.api

import com.example.shufuroomapp.features.auth.*
import com.example.shufuroomapp.features.profile.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/profile/me")
    fun getMyProfile(
        @Header("Authorization") token: String
    ): Call<UserProfile>

    @PUT("api/profile/me")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: EditProfileRequest
    ): Call<UpdateProfileResponse>

    @POST("api/auth/change-password")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Call<MessageResponse>
}