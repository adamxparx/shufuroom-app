package com.example.shufuroomapp.features.requests.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shufuroomapp.databinding.ItemBookingRequestBinding
import com.example.shufuroomapp.features.requests.data.HostBookingResponse

class BookingRequestsAdapter(
    private var allBookings: List<HostBookingResponse>,
    private val onStatusChange: (Int, String) -> Unit // (BookingId, NewStatus)
) : RecyclerView.Adapter<BookingRequestsAdapter.RequestViewHolder>() {

    private var filteredBookings: List<HostBookingResponse> = allBookings

    class RequestViewHolder(val binding: ItemBookingRequestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemBookingRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val item = filteredBookings[position]
        val booking = item.booking

        // 1. Set Text Data
        holder.binding.tvGuestName.text = item.guestName
        holder.binding.tvGuestInitial.text = item.guestName.firstOrNull()?.uppercase() ?: "?"
        holder.binding.tvBookingDates.text = "${booking.checkInDate} → ${booking.checkOutDate}"
        holder.binding.tvStatusBadge.text = booking.status

        // 2. Hide/Show Buttons based on Status mapped to your React design
        holder.binding.llActionButtons.visibility = View.VISIBLE

        when (booking.status) {
            "PENDING" -> {
                holder.binding.tvStatusBadge.setBackgroundColor(Color.parseColor("#FF9800")) // Orange
                holder.binding.btnReject.visibility = View.VISIBLE
                holder.binding.btnApprove.visibility = View.VISIBLE
                holder.binding.btnApprove.text = "Approve"
                holder.binding.btnApprove.setBackgroundColor(Color.parseColor("#388E3C")) // Green
            }
            "APPROVED" -> { // Upcoming
                holder.binding.tvStatusBadge.setBackgroundColor(Color.parseColor("#1976D2")) // Blue
                holder.binding.btnReject.visibility = View.GONE
                holder.binding.btnApprove.visibility = View.VISIBLE
                holder.binding.btnApprove.text = "Check In"
                holder.binding.btnApprove.setBackgroundColor(Color.parseColor("#1976D2"))
            }
            "CHECKED_IN" -> {
                holder.binding.tvStatusBadge.setBackgroundColor(Color.parseColor("#7B1FA2")) // Purple
                holder.binding.btnReject.visibility = View.GONE
                holder.binding.btnApprove.visibility = View.VISIBLE
                holder.binding.btnApprove.text = "Check Out"
                holder.binding.btnApprove.setBackgroundColor(Color.parseColor("#7B1FA2"))
            }
            "CHECKED_OUT", "REJECTED" -> { // Completed or Cancelled
                if (booking.status == "CHECKED_OUT") {
                    holder.binding.tvStatusBadge.setBackgroundColor(Color.parseColor("#388E3C")) // Green
                } else {
                    holder.binding.tvStatusBadge.setBackgroundColor(Color.parseColor("#D32F2F")) // Red
                }
                holder.binding.llActionButtons.visibility = View.GONE // No actions left!
            }
        }

        // 3. Click Listeners
        holder.binding.btnApprove.setOnClickListener {
            val nextStatus = when (booking.status) {
                "PENDING" -> "APPROVED"
                "APPROVED" -> "CHECKED_IN"
                "CHECKED_IN" -> "CHECKED_OUT"
                else -> booking.status
            }
            onStatusChange(booking.id, nextStatus)
        }

        holder.binding.btnReject.setOnClickListener {
            onStatusChange(booking.id, "REJECTED")
        }
    }

    override fun getItemCount(): Int = filteredBookings.size

    // Updates the full master list (used when API finishes loading)
    fun updateData(newBookings: List<HostBookingResponse>, currentFilter: String) {
        allBookings = newBookings
        filterByStatus(currentFilter)
    }

    // Filters the current list based on the TabLayout selection!
    fun filterByStatus(statusFilter: String) {
        filteredBookings = if (statusFilter == "ALL") {
            allBookings
        } else {
            allBookings.filter { it.booking.status == statusFilter }
        }
        notifyDataSetChanged()
    }
}