package com.example.shufuroomapp.features.rooms.booking.data

import com.example.shufuroomapp.features.dashboard.data.RoomResponse

data class BookingResponse(
    val id: Int,
    val roomId: Int,
    val checkInDate: String,
    val checkOutDate: String,
    val status: String, // e.g., "PENDING", "CONFIRMED"
    val room: RoomResponse? // Spring Boot might include the room details so we can show the image!
)