package com.example.shufuroomapp.features.rooms.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.shufuroomapp.databinding.ActivityRoomDetailsBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.rooms.booking.BookingFormActivity

class RoomDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomDetailsBinding
    private var room: RoomResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Unpack the Room object from the Intent
        room = intent.getSerializableExtra("EXTRA_ROOM") as? RoomResponse

        if (room == null) {
            Toast.makeText(this, "Error loading room details", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Populate the UI
        binding.tvDetailTitle.text = room?.name
        binding.tvDetailDescription.text = room?.description
        binding.tvDetailPrice.text = "$${room?.price} / night"
        binding.tvDetailBeds.text = "${room?.beds} Bed${if (room?.beds!! > 1) "s" else ""}"

        // Load the image using Coil
        if (!room?.imageUrl.isNullOrEmpty()) {
            binding.ivDetailImage.load(room?.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
            }
        }

        // The booking button listener (We will wire this to BookingFormActivity next!)
        binding.btnBookNow.setOnClickListener {
            val intent = Intent(this, BookingFormActivity::class.java)
            intent.putExtra("EXTRA_ROOM", room)
            startActivity(intent)
        }

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}