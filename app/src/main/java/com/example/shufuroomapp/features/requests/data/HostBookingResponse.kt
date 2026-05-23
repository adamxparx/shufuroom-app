package com.example.shufuroomapp.features.requests.data

data class HostBookingResponse(
    val booking: BookingDetail,
    val guestName: String
)

data class BookingDetail(
    val id: Int,
    val roomId: Int,
    val guestId: String,
    val checkInDate: String,
    val checkOutDate: String,
    val status: String,
    val createdAt: String
)