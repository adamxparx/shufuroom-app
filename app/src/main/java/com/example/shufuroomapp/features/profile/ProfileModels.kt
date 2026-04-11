package com.example.shufuroomapp.features.profile

// Response from /api/profile/me
data class UserProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)

// Request for PUT /api/profile/me
data class EditProfileRequest(
    val firstName: String?,
    val lastName: String?
)

// Response for Update
data class UpdateProfileResponse(
    val message: String,
    val updatedProfile: UserProfile
)
