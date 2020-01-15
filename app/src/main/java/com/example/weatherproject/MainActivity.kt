package com.example.weatherproject

import DailyDataItem
import DarkSkyService
import ForecastResponse
import HourlyDataItem
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.utils.WeatherIcon
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_currently.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val service = DarkSkyService(this)
    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val city = sharedPreferences.getString("city", "Linz")
        val latitude = sharedPreferences.getString("latitude", "48.30693999999999")
        val longitude = sharedPreferences.getString("longitude", "14.28583")

        dialog = Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_splash)
        dialog.show()

        getForecast(latitude, longitude, city)
        currentCity.setOnClickListener {
            currentCity.setOnClickListener {
                val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setTypeFilter(TypeFilter.CITIES)
                    .build(this)
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    val latLng = place.latLng
                    val latitude = latLng?.latitude.toString()
                    val longitude = latLng?.longitude.toString()
                    val sharedPreferences: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("latitude", latitude)
                    editor.putString("longitude", longitude)
                    editor.putString("city", place.name)
                    editor.apply()
                    getForecast(latitude, longitude, place.name!!)
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("HI", status.statusMessage)
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }

    private fun getForecast(latitude: String, longitude: String, city: String) {
        service.getForecast(latitude, longitude).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                dialog.dismiss()
                if (response.body() != null) {
                    current.visibility = View.VISIBLE
                    val currently = response.body()!!.currently
                    currentCity.text = city
                    current_weather_icon.setImageResource(WeatherIcon.get(currently?.icon))
                    currentTemp.text = currently?.temperature?.roundToInt().toString() + "°"
                    rain_value.text = currently?.precipProbability?.roundToInt().toString() + "%"
                    humidity_value.text = (currently?.humidity?.times(100)).toString() + "%"
                    wind_speed_value.text = currently?.windSpeed?.roundToInt().toString() + " mph"
                    air_pressure_value.text = currently?.pressure?.roundToInt().toString() + "hPa"
                    hourlyForecast(response.body()!!.hourly?.data)
                    dailyForecast(response.body()!!.daily?.data)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("Tag", "error")
            }
        })
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
