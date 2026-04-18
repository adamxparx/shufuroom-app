package com.example.shufuroomapp.features.auth.register

interface RegisterContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun onRegisterSuccess(message: String)
        fun onRegisterError(error: String)
    }

    interface Presenter {
        fun register(
            fName: String,
            lName: String,
            email: String,
            pass: String,
            cPass: String
        )
        fun onDestroy()
    }

}