package com.example.shufuroomapp.features.bookings

import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse

interface BookingsContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMyBookings(bookings: List<BookingResponse>)
        fun showError(message: String)
    }
    interface Presenter {
        fun fetchMyBookings()
        fun onDestroy()
    }
}