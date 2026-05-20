package com.example.shufuroomapp.features.auth.login.data

data class LoginResponse(
    val message: String,
    val token: String?,
    val user: UserData?,
)

data class UserData(
    val id: String,
    val email: String,
    val user_metadata: UserMetadata?
)

data class UserMetadata(
    val firstName: String?,
    val lastName: String?
)