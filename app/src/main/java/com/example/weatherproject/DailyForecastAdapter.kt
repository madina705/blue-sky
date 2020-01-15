package com.example.weatherproject


import DailyDataItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.utils.WeatherIcon
import kotlinx.android.synthetic.main.list_item_daily.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastAdapter.DailyViewHolder>() {

    private val dailyItems = mutableListOf<DailyDataItem?>()

    fun updateList(items: List<DailyDataItem?>) {
        dailyItems.clear()
        dailyItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class DailyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayOfWeek = view.dayOfWeek
        val date = view.date
        val minTemp = view.nightTemp
        val icon = view.daily_weather_icon
        val maxTemp = view.dayTemp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_daily, parent, false))
    }

    override fun getItemCount() = dailyItems.size

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = dailyItems[position]
        val date = Date(item?.time!! * 1000)
        val dayFormatter = SimpleDateFormat("EEEE", Locale.US)
        val dateFormatter = SimpleDateFormat("MMM d", Locale.US)
        holder.dayOfWeek.text = dayFormatter.format(date)
        holder.date.text = dateFormatter.format(date)
        holder.minTemp.text = item?.temperatureMin?.roundToInt().toString() + "°"
        holder.maxTemp.text = item?.temperatureMax?.roundToInt().toString() + "°"
        holder.icon.setImageResource(WeatherIcon.get(dailyItems[position]?.icon))
    }

}