package com.example.shufuroomapp.features.auth.register

import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.features.auth.register.data.RegisterRequest
import com.example.shufuroomapp.features.auth.register.data.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {

    fun performRegister(
        request: RegisterRequest,
        onResult: (Boolean, String) -> Unit
    ) {

        RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    onResult(true, "Account created! Please login.")
                } else {
                    onResult(false, "Registration failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onResult(false, "Network Error: ${t.message}")
            }

        })
    }

}