package com.example.weatherproject.screens.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.R
import com.example.weatherproject.model.FavoriteLocation
import com.example.weatherproject.screens.forecast.ForecastDetailsActivity
import com.example.weatherproject.utils.CustomFormatter
import com.example.weatherproject.utils.WeatherIcon
import kotlinx.android.synthetic.main.list_item_favorite_location.view.*
import kotlinx.android.synthetic.main.list_item_hourly.view.time


class FavoriteLocationAdapter(var context: Context) :
    RecyclerView.Adapter<FavoriteLocationAdapter.FavoriteLocationViewHolder>() {

    private val forecastItems = mutableListOf<FavoriteLocation?>()

    fun updateList(items: List<FavoriteLocation?>) {
        forecastItems.clear()
        forecastItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class FavoriteLocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time = view.time
        val icon = view.weather_icon
        val temp = view.temp
        val city = view.city
        var parentLayout: ConstraintLayout? = view.parent_layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteLocationViewHolder {
        return FavoriteLocationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_favorite_location,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = forecastItems.size

    override fun onBindViewHolder(holder: FavoriteLocationViewHolder, position: Int) {
        val currently = forecastItems[position]?.forecastResponse?.currently
        holder.time.text = currently?.summary
        holder.icon.setImageResource(WeatherIcon.get(currently?.icon))
        holder.temp.text = CustomFormatter.formatTemp(currently?.temperature)
        holder.city.text = forecastItems[position]?.city
        holder.parentLayout?.setOnClickListener {
            val intent = Intent(context, ForecastDetailsActivity::class.java)
            intent.putExtra("forecastItem", forecastItems[position] as FavoriteLocation)
            context.startActivity(intent)
        }
    }

    fun removeItem(position: Int) {
        this.forecastItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }
}