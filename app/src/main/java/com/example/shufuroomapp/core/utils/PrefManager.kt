package com.example.shufuroomapp.core.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SHUFU_PREFS", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("AUTH_TOKEN", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("AUTH_TOKEN", null)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}