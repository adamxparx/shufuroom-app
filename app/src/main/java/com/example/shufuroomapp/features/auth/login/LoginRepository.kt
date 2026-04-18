package com.example.shufuroomapp.features.auth.login

import android.content.Context
import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.features.auth.login.data.LoginRequest
import com.example.shufuroomapp.features.auth.login.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val context: Context) {
    fun performLogin(email: String, pass: String, onResult: (Boolean, String) -> Unit) {

        val request = LoginRequest(email, pass)

        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    val body = response.body()
                    val token = body?.token

                    if (token != null) {
                        val prefManager = PrefManager(context)
                        prefManager.saveToken(token)
                        onResult(true, "Login Successful")
                    } else {
                        onResult(false, "Token not found in response")
                    }
                } else {
                    onResult(false, "Invalid Email or Password")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, "Network error: ${t.message}")
            }
        })
    }
}