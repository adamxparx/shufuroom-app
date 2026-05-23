package com.example.shufuroomapp.features.bookings

import com.example.shufuroomapp.core.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BookingsPresenter(private var view: BookingsContract.View?) : BookingsContract.Presenter {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun fetchMyBookings() {
        view?.showLoading()
        scope.launch {
            try {
                val response = RetrofitClient.instance.getMyBookings()
                if (response.isSuccessful && response.body() != null) {
                    view?.showMyBookings(response.body()!!)
                } else {
                    view?.showError("Failed to load bookings")
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
        job.cancel()
    }
}