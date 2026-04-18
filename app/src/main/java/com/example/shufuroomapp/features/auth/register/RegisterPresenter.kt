package com.example.shufuroomapp.features.auth.register

import android.util.Patterns
import com.example.shufuroomapp.features.auth.register.data.RegisterRequest

class RegisterPresenter(
    private var view: RegisterContract.View?,
    private val repository: RegisterRepository
) : RegisterContract.Presenter {
    override fun register(
        fName: String,
        lName: String,
        email: String,
        pass: String,
        cPass: String
    ) {

        if (fName.isEmpty() ||
            lName.isEmpty() ||
            email.isEmpty() ||
            pass.isEmpty() ||
            cPass.isEmpty()) {
            view?.onRegisterError("Please fill all fields")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view?.onRegisterError("Invalid email format")
            return
        }

        if (pass != cPass) {
            view?.onRegisterError("Passwords do not match")
            return
        }

        if (pass.length < 8) {
            view?.onRegisterError("Password is too short")
            return
        }

        view?.showLoading()
        val request = RegisterRequest(email, pass, fName, lName)

        repository.performRegister(request) { success, message ->
            view?.hideLoading()
            if (success) {
                view?.onRegisterSuccess(message)
            } else {
                view?.onRegisterError(message)
            }
        }

    }

    override fun onDestroy() {
        view = null
    }


}