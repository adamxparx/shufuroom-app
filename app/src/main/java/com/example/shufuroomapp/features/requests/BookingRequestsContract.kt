package com.example.shufuroomapp.features.requests

import com.example.shufuroomapp.features.requests.data.HostBookingResponse

interface BookingRequestsContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showRequests(requests: List<HostBookingResponse>)
        fun onStatusUpdatedSuccess()
        fun showError(message: String)
    }

    interface Presenter {
        fun fetchRequests(roomId: Int)
        fun updateBookingStatus(bookingId: Int, newStatus: String)
        fun onDestroy()
    }
}