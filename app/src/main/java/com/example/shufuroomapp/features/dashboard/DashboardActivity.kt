package com.example.shufuroomapp.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.R
import com.example.shufuroomapp.features.auth.LoginActivity
import com.example.shufuroomapp.features.profile.ProfileActivity
import com.example.shufuroomapp.core.utils.PrefManager

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val prefManager = PrefManager(this)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnProfile = findViewById<Button>(R.id.btnProfile)

        btnLogout.setOnClickListener {
            prefManager.clear() // Remove the Bearer Token
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}