package com.example.shufuroomapp.features.dashboard.fragments

import com.example.shufuroomapp.core.api.RetrofitClient
import kotlinx.coroutines.*

class HomePresenter(private var view: HomeContract.View?) : HomeContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun fetchRooms() {
        view?.showLoading()

        scope.launch {
            try {
                // Make the network call to Spring Boot
                val response = RetrofitClient.instance.getRooms()

                if (response.isSuccessful && response.body() != null) {
                    // Send the downloaded list of rooms to the Fragment
                    view?.showRooms(response.body()!!)
                } else {
                    view?.showError("Failed to load rooms: ${response.code()}")
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
        job.cancel() // Stop the network call if the user leaves the screen
    }
}