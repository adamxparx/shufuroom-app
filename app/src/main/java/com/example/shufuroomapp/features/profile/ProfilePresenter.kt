package com.example.shufuroomapp.features.profile

import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.core.utils.PrefManager
import kotlinx.coroutines.*

class ProfilePresenter(
    private var view: ProfileContract.View?,
    private var prefManager: PrefManager
) : ProfileContract.Presenter {

    // Add Coroutine setup for network calls
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun logout() {
        android.util.Log.d("LOGOUT_DEBUG", "2. Presenter: logout() logic started")
        prefManager.clear()
        view?.navigateToLogin()
    }

    override fun fetchMyRooms() {
        view?.showLoading()

        scope.launch {
            try {
                // Call the endpoint
                val response = RetrofitClient.instance.getMyListings()

                if (response.isSuccessful && response.body() != null) {
                    view?.showMyRooms(response.body()!!)
                } else {
                    view?.showError("Failed to load your listings: ${response.code()}")
                }
            } catch (e: Exception) {
                view?.showError("Network error: ${e.localizedMessage}")
            } finally {
                view?.hideLoading()
            }
        }
    }

    override fun onDestroy() {
        view = null
        job.cancel() // Cancel network calls if user leaves
    }
}