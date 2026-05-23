package com.example.shufuroomapp.features.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shufuroomapp.databinding.ItemGridRoomBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.requests.BookingRequestsActivity

class ProfileGridAdapter(private var rooms: List<RoomResponse>) :
    RecyclerView.Adapter<ProfileGridAdapter.GridViewHolder>() {

    class GridViewHolder(val binding: ItemGridRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGridRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val room = rooms[position]

        // 1. Load the Image
        if (!room.imageUrl.isNullOrEmpty()) {
            holder.binding.ivGridImage.load(room.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
            }
        }

        // 2. NEW: The Click Listener for the Host Dashboard
        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, BookingRequestsActivity::class.java)
            // Pass the specific room that was clicked over to the new screen
            intent.putExtra("EXTRA_ROOM", room)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = rooms.size

    fun updateData(newRooms: List<RoomResponse>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}