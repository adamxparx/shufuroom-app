package com.example.shufuroomapp.features.profile

import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse

interface ProfileContract {

    interface View {
        fun navigateToLogin()

        fun showLoading()
        fun hideLoading()
        fun showMyRooms(rooms: List<RoomResponse>)
        fun showError(message: String)
    }

    interface Presenter {
        fun logout()

        fun fetchMyRooms()

        fun onDestroy()
    }
}