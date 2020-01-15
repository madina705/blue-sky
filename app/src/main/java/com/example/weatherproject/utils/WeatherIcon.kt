package com.example.weatherproject.utils

import com.example.weatherproject.R

object WeatherIcon {

    fun get(icon: String?): Int {

        return when (icon) {
            "cloudy" -> R.drawable.cloudy
            "partly-cloudy-day" -> R.drawable.partly_cloudy_day
            "partly-cloudy-night" -> R.drawable.partly_cloudy_night
            "hail" -> R.drawable.hail
            "tornado" -> R.drawable.tornado
            "wind" -> R.drawable.wind
            "sleet" -> R.drawable.sleet
            "clear-day" -> R.drawable.clear_day
            "snow" -> R.drawable.snow
            "rain" -> R.drawable.rain
            "thunderstorm" -> R.drawable.thunderstorm
            "clear-night" -> R.drawable.clear_night
            "fog" -> R.drawable.fog
            else ->  R.drawable.cloudy
        }

    }
}

