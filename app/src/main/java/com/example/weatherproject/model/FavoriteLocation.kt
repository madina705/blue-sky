package com.example.weatherproject.model
import ForecastResponse
import java.io.Serializable

data class FavoriteLocation(val forecastResponse: ForecastResponse, val city: String): Serializable