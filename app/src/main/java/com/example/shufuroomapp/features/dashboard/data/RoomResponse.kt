package com.example.shufuroomapp.features.dashboard.data

import java.io.Serializable

data class RoomResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String?,
    val beds: Int,
    val hostId: String,
    val createdAt: String
) : Serializable