package com.example.shufuroomapp.features.rooms.booking

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.databinding.ActivityBookingFormBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class BookingFormActivity : AppCompatActivity(), BookingContract.View {

    private lateinit var binding: ActivityBookingFormBinding
    private lateinit var presenter: BookingContract.Presenter
    private var room: RoomResponse? = null

    // Variables to hold the formatted dates for Spring Boot
    private var formattedCheckIn: String = ""
    private var formattedCheckOut: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = BookingPresenter(this)

        // 1. Get the Room from the Intent
        room = intent.getSerializableExtra("EXTRA_ROOM") as? RoomResponse
        if (room == null) {
            Toast.makeText(this, "Room error", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. Setup UI
        binding.toolbarBooking.setNavigationOnClickListener { finish() }
        binding.tvBookingRoomName.text = room?.name
        binding.tvBookingPrice.text = "$${room?.price} / night"

        // 3. Setup the Date Range Picker
        binding.btnSelectDates.setOnClickListener {
            showDateRangePicker()
        }

        // 4. Submit Booking
        binding.btnConfirmBooking.setOnClickListener {
            room?.id?.let { roomId ->
                presenter.submitBooking(roomId, formattedCheckIn, formattedCheckOut)
            }
        }
    }

    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Dates")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            // Convert timestamps to yyyy-MM-dd for Spring Boot
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC") // Crucial for MaterialDatePicker

            formattedCheckIn = sdf.format(Date(selection.first))
            formattedCheckOut = sdf.format(Date(selection.second))

            // Show friendly text to the user
            binding.tvSelectedDates.text = "$formattedCheckIn  to  $formattedCheckOut"
        }

        dateRangePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    override fun showLoading() {
        binding.btnConfirmBooking.isEnabled = false
        binding.btnConfirmBooking.text = "Booking..."
    }

    override fun hideLoading() {
        binding.btnConfirmBooking.isEnabled = true
        binding.btnConfirmBooking.text = "Confirm Booking"
    }

    override fun onBookingSuccess() {
        Toast.makeText(this, "Room Booked Successfully!", Toast.LENGTH_LONG).show()
        finish() // Closes the booking screen and returns to details
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}