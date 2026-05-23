package com.example.shufuroomapp.features.requests

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.databinding.ActivityBookingRequestsBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.requests.adapter.BookingRequestsAdapter
import com.example.shufuroomapp.features.requests.data.HostBookingResponse
import com.google.android.material.tabs.TabLayout

class BookingRequestsActivity : AppCompatActivity(), BookingRequestsContract.View {

    private lateinit var binding: ActivityBookingRequestsBinding
    private lateinit var presenter: BookingRequestsContract.Presenter
    private lateinit var adapter: BookingRequestsAdapter

    private var room: RoomResponse? = null

    // Keep track of the currently selected tab so we can re-apply the filter after a status update
    private var currentStatusFilter = "PENDING"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = BookingRequestsPresenter(this)

        // 1. Get Room Details from Intent
        room = intent.getSerializableExtra("EXTRA_ROOM") as? RoomResponse
        if (room == null) {
            Toast.makeText(this, "Room error", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. Setup UI
        binding.toolbarRequests.setNavigationOnClickListener { finish() }
        binding.tvRoomTitle.text = room?.name

        // 3. Setup Adapter with the click listener lambda
        adapter = BookingRequestsAdapter(emptyList()) { bookingId, newStatus ->
            // This runs whenever a host clicks Approve, Reject, Check In, etc.
            presenter.updateBookingStatus(bookingId, newStatus)
        }
        binding.rvRequests.adapter = adapter

        // 4. Setup Tabs to match your Spring Boot statuses
        binding.tabLayoutRequests.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentStatusFilter = when (tab?.position) {
                    0 -> "PENDING"
                    1 -> "APPROVED"      // Upcoming
                    2 -> "CHECKED_IN"
                    3 -> "CHECKED_OUT"   // Completed
                    4 -> "REJECTED"      // Cancelled
                    else -> "PENDING"
                }
                adapter.filterByStatus(currentStatusFilter)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 5. Fetch Initial Data
        room?.id?.let { presenter.fetchRequests(it) }
    }

    override fun showRequests(requests: List<HostBookingResponse>) {
        // Pass the data to the adapter and immediately apply the active tab's filter
        adapter.updateData(requests, currentStatusFilter)
    }

    override fun onStatusUpdatedSuccess() {
        Toast.makeText(this, "Status updated!", Toast.LENGTH_SHORT).show()
        // Re-fetch the data from the server so the list reflects the new status
        room?.id?.let { presenter.fetchRequests(it) }
    }

    override fun showLoading() {
        // Optional: Show progress bar
    }

    override fun hideLoading() {
        // Optional: Hide progress bar
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}