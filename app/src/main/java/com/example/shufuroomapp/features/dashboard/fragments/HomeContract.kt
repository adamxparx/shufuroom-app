package com.example.shufuroomapp.features.dashboard.fragments

import com.example.shufuroomapp.features.dashboard.data.RoomResponse

interface HomeContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showRooms(rooms: List<RoomResponse>)
        fun showError(message: String)
    }

    interface Presenter {
        fun fetchRooms()
        fun onDestroy()
    }
}