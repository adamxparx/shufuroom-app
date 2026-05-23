package com.example.shufuroomapp.features.rooms.booking.data

data class BookingRequest(
    val roomId: Int,
    val checkInDate: String, // Format: yyyy-MM-dd
    val checkOutDate: String // Format: yyyy-MM-dd
)