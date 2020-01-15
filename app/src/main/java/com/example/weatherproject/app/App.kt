package com.example.weatherproject.app

import android.app.Application
import com.google.android.libraries.places.api.Places

class App : Application() {

    val API_KEY = "AIzaSyA-PYz3s8XqseXpp5Sr4b3_ntqYuogl820"

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, API_KEY)
    }
}