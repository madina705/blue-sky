package com.example.weatherproject.screens.forecast

import DailyDataItem
import HourlyDataItem
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.R
import com.example.weatherproject.model.FavoriteLocation
import com.example.weatherproject.utils.CustomFormatter
import com.example.weatherproject.utils.WeatherIcon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_currently.*
import kotlin.math.roundToInt


class ForecastDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastItem = intent.extras!!.get("forecastItem") as FavoriteLocation
        populate(forecastItem)
    }

    private fun populate(item: FavoriteLocation) {
        current.visibility = View.VISIBLE
        val currently = item.forecastResponse.currently
        currentCity.text = item.city
        current_weather_icon.setImageResource(WeatherIcon.get(currently?.icon))
        currentTemp.text = CustomFormatter.formatTemp(currently?.temperature)
        rain_value.text = CustomFormatter.formatPrecipProbability(currently?.precipProbability)
        humidity_value.text = CustomFormatter.formatHumidity(currently?.humidity?.times(100))
        wind_speed_value.text = CustomFormatter.formatWindSpeed( currently?.windSpeed)
        air_pressure_value.text = CustomFormatter.formatPressure(currently?.pressure)
        hourlyForecast(item.forecastResponse.hourly?.data)
        dailyForecast(item.forecastResponse.daily?.data)
    }

    private fun hourlyForecast(items: List<HourlyDataItem?>?) {
        val adapter = HourlyForecastAdapter()
        recyclerHourly.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerHourly.adapter = adapter
        if (items != null) {
            adapter.updateList(items)
        }
    }

    private fun dailyForecast(items: List<DailyDataItem?>?) {
        val adapter = DailyForecastAdapter()
        recyclerDaily.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerDaily.adapter = adapter
        if (items != null) {
            adapter.updateList(items)
        }
    }
}
