package com.example.shufuroomapp.features.rooms.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.databinding.ActivityAddListingBinding

class AddListingActivity : AppCompatActivity(), AddListingContract.View {

    private lateinit var binding: ActivityAddListingBinding
    private lateinit var presenter: AddListingContract.Presenter

    // This will hold the URI of the image the user selects
    private var selectedImageUri: Uri? = null

    // Set up the modern Android Photo Picker
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // The user selected an image! Show it in the ImageView
            binding.ivRoomImage.setImageURI(uri)
            selectedImageUri = uri
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = AddListingPresenter(this)

        // 1. Handle the Back Arrow in the Toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish() // Closes the screen and goes back
        }

        // 2. Open gallery when they click the ImageView
        binding.ivRoomImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // 3. Publish Button
        binding.btnPublish.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val price = binding.etPrice.text.toString()
            val beds = binding.etBeds.text.toString() // Grab the beds

            presenter.submitListing(title, description, price, beds, selectedImageUri)
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPublish.isEnabled = false // Prevent double-clicking
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnPublish.isEnabled = true
    }

    override fun onListingAddedSuccessfully() {
        Toast.makeText(this, "Room added successfully!", Toast.LENGTH_SHORT).show()
        finish() // Close the activity and return to the Profile fragment
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy() // Clean up coroutines to prevent memory leaks
    }
}