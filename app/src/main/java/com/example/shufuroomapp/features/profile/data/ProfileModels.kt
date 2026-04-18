package com.example.shufuroomapp.features.profile.data

data class UserProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)

data class EditProfileRequest(
    val firstName: String?,
    val lastName: String?
)

data class UpdateProfileResponse(
    val message: String,
    val updatedProfile: UserProfile
)

// temporary
data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

data class MessageResponse(val message: String)
