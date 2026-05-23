package com.example.shufuroomapp.features.dashboard.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shufuroomapp.R
import com.example.shufuroomapp.databinding.ItemRoomBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.rooms.detail.RoomDetailsActivity

class RoomAdapter(private var rooms: List<RoomResponse>) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    // This class holds onto the individual views inside item_room.xml
    class RoomViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)

    // 1. Inflates (creates) the actual XML card layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    // 2. Plugs the Spring Boot data into the UI elements
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]

        holder.binding.tvRoomName.text = room.name
        // Adds an "s" if there is more than 1 bed
        holder.binding.tvRoomBeds.text = "${room.beds} Bed${if (room.beds > 1) "s" else ""}"
        holder.binding.tvRoomPrice.text = "$${room.price} / night"

        // 3. Coil magically downloads the Supabase image and caches it!
        if (!room.imageUrl.isNullOrEmpty()) {
            holder.binding.ivRoomImage.load(room.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray) // Shows while downloading
                error(android.R.drawable.ic_menu_report_image) // Shows if URL is broken
            }
        }

        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, RoomDetailsActivity::class.java)
            // Package the room object into the Intent
            intent.putExtra("EXTRA_ROOM", room)
            view.context.startActivity(intent)
        }
    }

    // Tells the RecyclerView exactly how many items we have
    override fun getItemCount(): Int = rooms.size

    // We will call this from our Presenter when new data arrives from the server
    fun updateData(newRooms: List<RoomResponse>) {
        rooms = newRooms
        notifyDataSetChanged() // Refreshes the UI instantly
    }
}