package com.example.shufuroomapp.features.rooms.booking

interface BookingContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onBookingSuccess()
        fun showError(message: String)
    }

    interface Presenter {
        fun submitBooking(roomId: Int, checkInDate: String, checkOutDate: String)
        fun onDestroy()
    }
}