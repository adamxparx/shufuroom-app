package com.example.shufuroomapp.features.profile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.FragmentProfileBinding
import com.example.shufuroomapp.features.auth.login.LoginActivity
import com.example.shufuroomapp.features.bookings.adapter.ProfilePresenter
import com.example.shufuroomapp.features.dashboard.data.RoomResponse
import com.example.shufuroomapp.features.profile.ProfileContract
import com.example.shufuroomapp.features.profile.adapter.ProfileGridAdapter
import com.example.shufuroomapp.features.rooms.add.AddListingActivity
import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse
import com.example.shufuroomapp.features.settings.SettingsActivity
import com.google.android.material.tabs.TabLayout

class ProfileFragment : Fragment(R.layout.fragment_profile), ProfileContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ProfileContract.Presenter
    private lateinit var gridAdapter: ProfileGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val prefManager = PrefManager(requireContext())
        presenter = ProfilePresenter(this, prefManager)
        gridAdapter = ProfileGridAdapter(emptyList())

        val firstName = prefManager.getFirstName() ?: "User"
        val lastName = prefManager.getLastName() ?: ""
        binding.tvProfileName.text = "$firstName $lastName"

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        binding.btnAddListing.setOnClickListener {
            startActivity(Intent(requireContext(), AddListingActivity::class.java))
        }

        binding.rvProfileContent.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvProfileContent.adapter = gridAdapter

        binding.tabLayoutProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.rvProfileContent.layoutManager = GridLayoutManager(requireContext(), 3)
                        binding.rvProfileContent.adapter = gridAdapter
                        presenter.fetchMyRooms()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        presenter.fetchMyRooms()
    }

    // --- OVERRIDES FOR THE DATA ---
    override fun showLoading() {
        // Optional: Show a progress bar
    }

    override fun hideLoading() {
        // Optional: Hide progress bar
    }

    override fun showMyRooms(rooms: List<RoomResponse>) {
        gridAdapter.updateData(rooms)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // --- LOGOUT OVERRIDE ---
    override fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }
}