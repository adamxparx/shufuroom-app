package com.example.shufuroomapp.features.bookings.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shufuroomapp.databinding.ItemBookingBinding
import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse

class BookingAdapter(private var bookings: List<BookingResponse>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(val binding: ItemBookingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]

        // THE FIX: If the room name is null, we display "Room #" + the ID instead!
        // Note: If your data class uses a different variable name for the ID, change 'booking.roomId' to match it (e.g., 'booking.room?.id' or 'booking.id')
        holder.binding.tvBookingRoomName.text = booking.room?.name ?: "Room #${booking.roomId}"

        holder.binding.tvBookingDates.text = "${booking.checkInDate} to ${booking.checkOutDate}"
        holder.binding.tvBookingStatus.text = booking.status

        // Upgrade: Let's use the nice global status colors we setup earlier for the host dashboard!
        when (booking.status) {
            "PENDING" -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#FF9800")) // Orange
            "APPROVED" -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#1976D2")) // Blue
            "CHECKED_IN" -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#7B1FA2")) // Purple
            "CHECKED_OUT" -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#388E3C")) // Green
            "REJECTED", "CANCELLED" -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#D32F2F")) // Red
            else -> holder.binding.tvBookingStatus.setBackgroundColor(Color.parseColor("#888888")) // Gray fallback
        }

        // Load image if available
        if (!booking.room?.imageUrl.isNullOrEmpty()) {
            holder.binding.ivBookingImage.load(booking.room?.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
            }
        }
    }

    override fun getItemCount(): Int = bookings.size

    fun updateData(newBookings: List<BookingResponse>) {
        bookings = newBookings
        notifyDataSetChanged()
    }
}