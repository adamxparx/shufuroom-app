package com.example.shufuroomapp.features.rooms.add

import android.content.Context
import com.example.shufuroomapp.ShufuRoomApplication
import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.features.rooms.add.data.AddRoomRequest
import kotlinx.coroutines.*

// ADDED: private val context: Context
class AddListingPresenter(
    private var view: AddListingContract.View?
) : AddListingContract.Presenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun submitListing(name: String, description: String, price: String, beds: String, imageUri: android.net.Uri?) {

        if (name.isBlank() || description.isBlank() || price.isBlank() || beds.isBlank()) {
            view?.showError("All fields are required")
            return
        }

        val priceValue = price.toDoubleOrNull()
        val bedsValue = beds.toIntOrNull() // Parse the beds

        if (priceValue == null) {
            view?.showError("Please enter a valid number for the price")
            return
        }

        if (bedsValue == null) {
            view?.showError("Please enter a valid whole number for beds")
            return
        }

        if (imageUri == null) {
            view?.showError("Please select an image for the room")
            return
        }

        view?.showLoading()

        scope.launch {
            try {
                val uploadedUrl = com.example.shufuroomapp.core.api.SupabaseClient.uploadImage(
                    ShufuRoomApplication.appContext, imageUri)

                if (uploadedUrl == null) {
                    view?.showError("Failed to upload image")
                    view?.hideLoading()
                    return@launch
                }

                // Pass bedsValue into the request!
                val request = AddRoomRequest(name, description, priceValue, uploadedUrl, bedsValue)
                val response = RetrofitClient.instance.addRoom(request)

                if (response.isSuccessful) {
                    view?.onListingAddedSuccessfully()
                } else {
                    view?.showError("Failed to add listing: ${response.code()}")
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