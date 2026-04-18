package com.example.shufuroomapp.features.auth.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onLoginSuccess(message: String)
        fun onLoginError(error: String)
        fun navigateToRegister()
    }

    interface Presenter {
        fun login(email: String, pass: String)
        fun onDestroy()
    }
}