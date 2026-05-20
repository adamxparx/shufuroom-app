package com.example.shufuroomapp.features.rooms.add

interface AddListingContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onListingAddedSuccessfully()
        fun showError(message: String)
    }

    interface Presenter {
        fun submitListing(title: String, description: String, price: String, beds: String, imageUri: android.net.Uri?)
        fun onDestroy()
    }
}