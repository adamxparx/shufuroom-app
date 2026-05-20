package com.example.shufuroomapp.features.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val prefManager = PrefManager(requireContext())
        val firstName = prefManager.getFirstName() ?: "User"
        
        binding.tvHomeWelcome.text = "Welcome $firstName"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}