package com.example.weatherproject.utils

import kotlin.math.roundToInt

object CustomFormatter {
    fun formatTemp(temp: Double?) : String {
        return temp?.roundToInt().toString() + "°"
    }

    fun formatPrecipProbability(precipProbability: Double?) : String {
        return precipProbability?.roundToInt().toString() + "%"
    }

    fun formatHumidity(humidity: Double?) : String {
        return humidity?.toString() + "%"
    }

    fun formatWindSpeed(WindSpeed: Double?) : String {
        return WindSpeed?.roundToInt().toString() + " mph"
    }

    fun formatPressure(pressure: Double?) : String {
        return pressure?.roundToInt().toString() + " hPa"
    }
}
