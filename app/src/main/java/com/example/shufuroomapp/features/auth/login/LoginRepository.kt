package com.example.shufuroomapp.features.auth.login

import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.features.auth.login.data.LoginRequest
import com.example.shufuroomapp.features.auth.login.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    fun performLogin(email: String, pass: String, onResult: (Boolean, String) -> Unit) {

        val request = LoginRequest(email, pass)

        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    onResult(true, loginResponse?.message ?: "Login Successful")
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