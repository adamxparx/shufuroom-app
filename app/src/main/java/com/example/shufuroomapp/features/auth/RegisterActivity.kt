package com.example.shufuroomapp.features.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.R
import com.example.shufuroomapp.core.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val progressBar = findViewById<ProgressBar>(R.id.regProgressBar)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnRegister.setOnClickListener {
            val fName = findViewById<EditText>(R.id.etFirstName).text.toString().trim()
            val lName = findViewById<EditText>(R.id.etLastName).text.toString().trim()
            val email = findViewById<EditText>(R.id.etRegEmail).text.toString().trim()
            val pass = findViewById<EditText>(R.id.etRegPassword).text.toString().trim()
            val cpass = findViewById<EditText>(R.id.etRegConfirmPassword).text.toString().trim()

            // Simple validation
            if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != cpass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false

            val request = RegisterRequest(email, pass, fName, lName)

            RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true

                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Account Created! Please Login.", Toast.LENGTH_LONG).show()
                        finish() // Returns user to Login screen
                    } else {
                        // Handle 400 Bad Request or existing user
                        Toast.makeText(this@RegisterActivity, "Registration Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true
                    Toast.makeText(this@RegisterActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
