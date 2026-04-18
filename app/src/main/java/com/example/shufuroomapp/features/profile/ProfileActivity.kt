package com.example.shufuroomapp.features.profile

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.features.auth.ChangePasswordRequest
import com.example.shufuroomapp.features.auth.MessageResponse
import com.example.shufuroomapp.features.profile.data.EditProfileRequest
import com.example.shufuroomapp.features.profile.data.UpdateProfileResponse
import com.example.shufuroomapp.features.profile.data.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var tvEmail: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        prefManager = PrefManager(this)

        // 1. INITIALIZE ALL VIEWS FIRST
        etFirstName = findViewById(R.id.etProfileFirstName)
        etLastName = findViewById(R.id.etProfileLastName)
        tvEmail = findViewById(R.id.tvProfileEmail)
        progressBar = findViewById(R.id.profileProgressBar)

        // These MUST be initialized before the listener below runs
        etCurrentPassword = findViewById(R.id.etCurrentPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword)


        val btnUpdateProfile = findViewById<Button>(R.id.btnUpdateProfile)
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)

        loadProfile()

        // 2. NOW SET THE LISTENERS
        btnUpdateProfile.setOnClickListener {
            updateProfile()
        }

        btnChangePassword.setOnClickListener {
            // Because etCurrentPassword was initialized above, this won't crash now
            handleChangePassword()
        }

    }


    private fun loadProfile() {
        val token = "Bearer ${prefManager.getToken()}"
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getMyProfile(token).enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val profile = response.body()
                    etFirstName.setText(profile?.firstName)
                    etLastName.setText(profile?.lastName)
                    tvEmail.text = "Email: ${profile?.email}"
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ProfileActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProfile() {
        val token = "Bearer ${prefManager.getToken()}"
        val request = EditProfileRequest(etFirstName.text.toString(), etLastName.text.toString())

        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.updateProfile(token, request).enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(call: Call<UpdateProfileResponse>, response: Response<UpdateProfileResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Profile Updated!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ProfileActivity, "Update failed", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun handleChangePassword() {
        val current = etCurrentPassword.text.toString()
        val newPw = etNewPassword.text.toString()
        val confirm = etConfirmNewPassword.text.toString()

        // Local validation
        if (current.isEmpty() || newPw.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPw != confirm) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val token = "Bearer ${prefManager.getToken()}"
        val request = ChangePasswordRequest(current, newPw)

        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.changePassword(token, request).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Password Updated Successfully!", Toast.LENGTH_SHORT).show()
                    // Clear fields on success
                    etCurrentPassword.text.clear()
                    etNewPassword.text.clear()
                    etConfirmNewPassword.text.clear()
                } else if (response.code() == 401) {
                    // This is triggered by verifyWithSupabase failing on the backend
                    Toast.makeText(this@ProfileActivity, "Current password is incorrect", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@ProfileActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ProfileActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
