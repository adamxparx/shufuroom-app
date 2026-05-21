package com.example.shufuroomapp.core.api

import com.example.shufuroomapp.features.auth.*
import com.example.shufuroomapp.features.auth.login.data.LoginRequest
import com.example.shufuroomapp.features.auth.login.data.LoginResponse
import com.example.shufuroomapp.features.auth.register.data.RegisterRequest
import com.example.shufuroomapp.features.auth.register.data.RegisterResponse
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.profile.data.ChangePasswordRequest
import com.example.shufuroomapp.features.profile.data.EditProfileRequest
import com.example.shufuroomapp.features.profile.data.MessageResponse
import com.example.shufuroomapp.features.profile.data.UpdateProfileResponse
import com.example.shufuroomapp.features.profile.data.UserProfile
import com.example.shufuroomapp.features.rooms.add.data.AddRoomRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("profile/me")
    fun getMyProfile(
        @Header("Authorization") token: String
    ): Call<UserProfile>

    @PUT("profile/me")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: EditProfileRequest
    ): Call<UpdateProfileResponse>

    @POST("auth/change-password")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Call<MessageResponse>

    @POST("rooms") // Matches your Spring Boot @PostMapping("/api/rooms")
    suspend fun addRoom(@Body request: AddRoomRequest): Response<Unit>

    @GET("/api/rooms")
    suspend fun getRooms(): retrofit2.Response<List<RoomResponse>>

    @GET("/api/rooms/my-listings")
    suspend fun getMyListings(): retrofit2.Response<List<RoomResponse>>
}