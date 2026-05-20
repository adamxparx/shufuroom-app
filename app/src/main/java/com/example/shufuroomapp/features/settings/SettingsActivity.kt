package com.example.shufuroomapp.features.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.ActivitySettingsBinding
import com.example.shufuroomapp.features.auth.login.LoginActivity

class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var presenter: SettingsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SettingsPresenter(this, PrefManager(this))

        presenter.loadCurrentProfile()

        setupToggleLogic()

        binding.btnUpdateProfile.setOnClickListener {
            presenter.updateProfile(
                binding.etProfileFirstName.text.toString(),
                binding.etProfileLastName.text.toString()
            )
        }

        binding.btnChangePassword.setOnClickListener {
            presenter.changePassword(
                binding.etCurrentPassword.text.toString(),
                binding.etNewPassword.text.toString(),
                binding.etConfirmNewPassword.text.toString()
            )
        }

        binding.btnLogout.setOnClickListener {
            presenter.logout()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupToggleLogic() {
        // Account Name Toggle
        binding.btnEditAccountName.setOnClickListener {
            binding.layoutEditAccountName.visibility = View.VISIBLE
            binding.tvDisplayAccountName.visibility = View.GONE
            binding.btnEditAccountName.visibility = View.GONE
            binding.btnCancelAccountName.visibility = View.VISIBLE
        }

        binding.btnCancelAccountName.setOnClickListener {
            binding.layoutEditAccountName.visibility = View.GONE
            binding.tvDisplayAccountName.visibility = View.VISIBLE
            binding.btnEditAccountName.visibility = View.VISIBLE
            binding.btnCancelAccountName.visibility = View.GONE
        }

        // Password Toggle
        binding.btnEditPassword.setOnClickListener {
            binding.layoutEditPassword.visibility = View.VISIBLE
            binding.tvDisplayPassword.visibility = View.GONE
            binding.btnEditPassword.visibility = View.GONE
            binding.btnCancelPassword.visibility = View.VISIBLE
        }

        binding.btnCancelPassword.setOnClickListener {
            binding.layoutEditPassword.visibility = View.GONE
            binding.tvDisplayPassword.visibility = View.VISIBLE
            binding.btnEditPassword.visibility = View.VISIBLE
            binding.btnCancelPassword.visibility = View.GONE
            clearPasswordFields()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // Collapse sections on success if needed
        if (message.contains("Success", ignoreCase = true)) {
            binding.btnCancelAccountName.performClick()
            binding.btnCancelPassword.performClick()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        binding.profileProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun clearPasswordFields() {
        binding.etCurrentPassword.text?.clear()
        binding.etNewPassword.text?.clear()
        binding.etConfirmNewPassword.text?.clear()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun populateProfile(firstName: String, lastName: String) {
        binding.etProfileFirstName.setText(firstName)
        binding.etProfileLastName.setText(lastName)
        binding.tvDisplayAccountName.text = "$firstName $lastName"
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}