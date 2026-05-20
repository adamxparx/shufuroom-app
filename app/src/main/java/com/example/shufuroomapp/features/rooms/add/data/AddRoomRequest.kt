package com.example.shufuroomapp.features.rooms.add.data

data class AddRoomRequest(
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val beds: Int
)