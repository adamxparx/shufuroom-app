package com.example.shufuroomapp.features.auth.register.data

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)