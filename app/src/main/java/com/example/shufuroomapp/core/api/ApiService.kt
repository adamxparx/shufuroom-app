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
import com.example.shufuroomapp.features.requests.data.HostBookingResponse
import com.example.shufuroomapp.features.rooms.add.data.AddRoomRequest
import com.example.shufuroomapp.features.rooms.booking.data.BookingRequest
import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("/api/profile/me")
    fun getMyProfile(
        @Header("Authorization") token: String
    ): Call<UserProfile>

    @PUT("/api/profile/me")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: EditProfileRequest
    ): Call<UpdateProfileResponse>

    @POST("/api/change-password")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Call<MessageResponse>

    @POST("rooms")
    suspend fun addRoom(@Body request: AddRoomRequest): Response<Unit>

    @GET("/api/rooms")
    suspend fun getRooms(): retrofit2.Response<List<RoomResponse>>

    @GET("/api/rooms/my-listings")
    suspend fun getMyListings(): retrofit2.Response<List<RoomResponse>>

    @POST("/api/bookings")
    suspend fun createBooking(@Body request: BookingRequest): retrofit2.Response<Void>

    @GET("/api/bookings/my-trips")
    suspend fun getMyBookings(): retrofit2.Response<List<BookingResponse>>

    @GET("/api/bookings/room/{roomId}")
    suspend fun getRoomBookings(@Path("roomId") roomId: Int): retrofit2.Response<List<HostBookingResponse>>

    @PUT("/api/bookings/{id}/status")
    suspend fun updateBookingStatus(
        @Path("id") bookingId: Int,
        @Query("status") status: String
    ): retrofit2.Response<Void>
}