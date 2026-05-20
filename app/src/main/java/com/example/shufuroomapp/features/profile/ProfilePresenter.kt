package com.example.shufuroomapp.features.profile

import com.example.shufuroomapp.core.utils.PrefManager

class ProfilePresenter(
    private var view: ProfileContract.View?,
    private var prefManager: PrefManager
) : ProfileContract.Presenter {
    override fun logout() {
        android.util.Log.d("LOGOUT_DEBUG", "2. Presenter: logout() logic started")
        prefManager.clear()
        view?.navigateToLogin()
    }

    override fun onDestroy() {
        view = null
    }


}