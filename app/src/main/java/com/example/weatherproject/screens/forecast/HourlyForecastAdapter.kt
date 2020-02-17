package com.example.weatherproject.screens.forecast

import HourlyDataItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.R
import com.example.weatherproject.utils.CustomFormatter
import com.example.weatherproject.utils.WeatherIcon
import kotlinx.android.synthetic.main.list_item_hourly.view.*
import java.text.SimpleDateFormat
import java.util.*


class HourlyForecastAdapter : RecyclerView.Adapter<HourlyForecastAdapter.HourlyViewHolder>() {

    private val hourlyItems = mutableListOf<HourlyDataItem?>()

    fun updateList(items: List<HourlyDataItem?>) {
        hourlyItems.clear()
        hourlyItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time = view.time
        val icon = view.hourly_weather_icon
        val temp = view.temperature
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_hourly, parent, false))
    }

    override fun getItemCount() = hourlyItems.size

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = hourlyItems[position]
        if (position == 0) {
            holder.time.text = "Now"
        } else {
            val date = Date(item?.time!! * 1000)
            val dateFormatter = SimpleDateFormat("h a", Locale.US)
            holder.time.text = dateFormatter.format(date).toLowerCase()
        }
        holder.icon.setImageResource(WeatherIcon.get(hourlyItems[position]?.icon))
        holder.temp.text = CustomFormatter.formatTemp(item?.temperature)
    }

}