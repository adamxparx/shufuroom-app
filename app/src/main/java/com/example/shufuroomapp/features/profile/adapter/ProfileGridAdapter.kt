package com.example.shufuroomapp.features.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shufuroomapp.databinding.ItemGridRoomBinding
import com.example.shufuroomapp.features.dashboard.data.RoomResponse

class ProfileGridAdapter(private var rooms: List<RoomResponse>) :
    RecyclerView.Adapter<ProfileGridAdapter.GridViewHolder>() {

    class GridViewHolder(val binding: ItemGridRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGridRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val room = rooms[position]

        if (!room.imageUrl.isNullOrEmpty()) {
            holder.binding.ivGridImage.load(room.imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
            }
        }
    }

    override fun getItemCount(): Int = rooms.size

    fun updateData(newRooms: List<RoomResponse>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}