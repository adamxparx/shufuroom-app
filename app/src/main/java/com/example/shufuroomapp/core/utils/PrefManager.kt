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

    fun saveFirstName(firstName: String) {
        sharedPreferences.edit().putString("FIRST_NAME", firstName).apply()
    }

    fun getFirstName(): String? {
        return sharedPreferences.getString("FIRST_NAME", null)
    }

    fun saveLastName(lastName: String) {
        sharedPreferences.edit().putString("LAST_NAME", lastName).apply()
    }

    fun getLastName(): String? {
        return sharedPreferences.getString("LAST_NAME", null)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}