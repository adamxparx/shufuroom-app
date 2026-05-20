package com.example.shufuroomapp.features.settings

interface SettingsContract {

    interface View {
        fun showMessage(message: String)
        fun showLoading(isLoading: Boolean)
        fun clearPasswordFields()
        fun navigateToLogin()
        fun populateProfile(firstName: String, lastName: String)
    }

    interface Presenter {
        fun loadCurrentProfile()
        fun updateProfile(firstName: String, lastName: String)
        fun changePassword(currentPass: String, newPass: String, confirmPass: String)
        fun logout()
        fun onDestroy()
    }

}