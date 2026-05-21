package com.example.shufuroomapp.features.dashboard.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.FragmentHomeBinding
import com.example.shufuroomapp.features.dashboard.adapter.RoomAdapter
import com.example.shufuroomapp.features.dashboard.data.RoomResponse

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var adapter: RoomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val prefManager = PrefManager(requireContext())
        val firstName = prefManager.getFirstName() ?: "User"
        binding.tvHomeWelcome.text = "Welcome $firstName"

        // 1. Setup the Adapter with an empty list at first
        adapter = RoomAdapter(emptyList())
        binding.rvRooms.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRooms.adapter = adapter

        // 2. Initialize the Presenter and tell it to fetch the data
        presenter = HomePresenter(this)
        presenter.fetchRooms()
    }

    override fun showLoading() {
        // You can add a ProgressBar to fragment_home.xml later and set it to VISIBLE here
    }

    override fun hideLoading() {
        // Set the ProgressBar to GONE here
    }

    override fun showRooms(rooms: List<RoomResponse>) {
        // Pass the live data from Spring Boot directly into the Adapter!
        adapter.updateData(rooms)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }
}