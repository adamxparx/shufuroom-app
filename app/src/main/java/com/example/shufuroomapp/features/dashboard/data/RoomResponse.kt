package com.example.shufuroomapp.features.dashboard.data

data class RoomResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String?, // Made nullable just in case a room has no image!
    val beds: Int,
    val hostId: String,
    val createdAt: String
)