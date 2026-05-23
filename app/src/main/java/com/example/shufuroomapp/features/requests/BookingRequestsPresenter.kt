package com.example.shufuroomapp.features.requests

import com.example.shufuroomapp.core.api.RetrofitClient
import kotlinx.coroutines.*

class BookingRequestsPresenter(private var view: BookingRequestsContract.View?) : BookingRequestsContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun fetchRequests(roomId: Int) {
        view?.showLoading()
        scope.launch {
            try {
                val response = RetrofitClient.instance.getRoomBookings(roomId)
                if (response.isSuccessful && response.body() != null) {
                    view?.showRequests(response.body()!!)
                } else {
                    view?.showError("Failed to load requests")
                }
            } catch (e: Exception) {
                view?.showError("Network error: ${e.localizedMessage}")
            } finally {
                view?.hideLoading()
            }
        }
    }

    override fun updateBookingStatus(bookingId: Int, newStatus: String) {
        view?.showLoading()
        scope.launch {
            try {
                val response = RetrofitClient.instance.updateBookingStatus(bookingId, newStatus)

                if (response.isSuccessful) {
                    view?.onStatusUpdatedSuccess()
                } else {
                    view?.showError("Failed to update status: ${response.code()}")
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