package com.example.shufuroomapp.features.profile

import com.example.shufuroomapp.features.dashboard.data.RoomResponse

interface ProfileContract {

    interface View {
        // Your existing method
        fun navigateToLogin()

        // New methods for the Grid
        fun showLoading()
        fun hideLoading()
        fun showMyRooms(rooms: List<RoomResponse>)
        fun showError(message: String)
    }

    interface Presenter {
        // Your existing method
        fun logout()

        // New method for the Grid
        fun fetchMyRooms()

        fun onDestroy()
    }
}