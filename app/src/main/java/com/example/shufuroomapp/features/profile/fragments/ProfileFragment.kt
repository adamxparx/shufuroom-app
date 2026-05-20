package com.example.shufuroomapp.features.profile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.FragmentProfileBinding
import com.example.shufuroomapp.features.auth.login.LoginActivity
import com.example.shufuroomapp.features.profile.ProfileContract
import com.example.shufuroomapp.features.profile.ProfilePresenter
import com.example.shufuroomapp.features.settings.SettingsActivity

class ProfileFragment : Fragment(R.layout.fragment_profile), ProfileContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: ProfileContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val prefManager = PrefManager(requireContext())
        presenter = ProfilePresenter(this, prefManager)

        val firstName = prefManager.getFirstName() ?: "User"
        val lastName = prefManager.getLastName() ?: ""
        binding.tvProfileName.text = "$firstName $lastName"

        binding.btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnAddListing.setOnClickListener {
            // Static button for now
            android.widget.Toast.makeText(requireContext(), "Add Listing coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

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