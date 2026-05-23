package com.example.shufuroomapp.features.rooms.booking

import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.features.rooms.booking.data.BookingRequest
import kotlinx.coroutines.*

class BookingPresenter(private var view: BookingContract.View?) : BookingContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun submitBooking(roomId: Int, checkInDate: String, checkOutDate: String) {
        if (checkInDate.isBlank() || checkOutDate.isBlank()) {
            view?.showError("Please select your dates")
            return
        }

        view?.showLoading()

        scope.launch {
            try {
                val request = BookingRequest(roomId, checkInDate, checkOutDate)
                val response = RetrofitClient.instance.createBooking(request)

                if (response.isSuccessful) {
                    view?.onBookingSuccess()
                } else {
                    view?.showError("Failed to book: ${response.code()}")
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