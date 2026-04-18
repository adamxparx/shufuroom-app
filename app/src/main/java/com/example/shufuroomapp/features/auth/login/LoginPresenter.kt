package com.example.shufuroomapp.features.auth.login

import android.util.Patterns

class LoginPresenter(
    private var view: LoginContract.View?,
    private val repository: LoginRepository
) : LoginContract.Presenter {
    override fun login(email: String, pass: String) {

        if (email.isEmpty() || pass.isEmpty()) {
            view?.onLoginError("Please fill in all fields")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view?.onLoginError("Please enter a valid email address")
            return
        }

        view?.showLoading()
        repository.performLogin(email, pass) { success, message ->
            view?.hideLoading()
            if (success) {
                view?.onLoginSuccess(message)
            } else {
                view?.onLoginError(message)
            }
        }

    }

    override fun onDestroy() {
        view = null
    }

}