package com.example.shufuroomapp.features.auth.login.data

data class LoginResponse(
    val message: String,
    val token: String?,
    val user: Any?,
)
