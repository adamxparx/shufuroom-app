package com.example.shufuroomapp

import android.app.Application
import android.content.Context

class ShufuRoomApplication : Application() {

    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}