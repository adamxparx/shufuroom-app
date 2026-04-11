package com.example.shufuroomapp.features.auth

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val message: String,
    val token: String?,
    val user: Any?,
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)

data class RegisterResponse(
    val message: String,
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

data class MessageResponse(val message: String)
