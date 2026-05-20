package com.example.shufuroomapp.features.profile

interface ProfileContract {

    interface View {
        fun navigateToLogin()
    }

    interface Presenter {
        fun logout()
        fun onDestroy()
    }

}