package com.example.weatherproject.screens.home

import DarkSkyService
import ForecastResponse
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.R
import com.example.weatherproject.model.FavoriteLocation
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.drawer_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var splashDialog: Dialog
    private val service = DarkSkyService(this)
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private var favoriteList = mutableListOf<FavoriteLocation>()
    private val defaultList = mutableListOf<FavoriteLocation>()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: FavoriteLocationAdapter
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)
        context = this
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

        // Splash Screen
        splashDialog = Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        splashDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        splashDialog.setCancelable(false)
        splashDialog.setContentView(R.layout.activity_splash)
        adapter = FavoriteLocationAdapter(context)

        // Favorite location adapter
        rvFavorites.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvFavorites.adapter = adapter

        getDefaultLocationForecast()

        favoriteList = getFavoriteListFromPrefs()
        adapter.updateList(favoriteList)

        addLocation.setOnClickListener {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.CITIES)
                .build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        // Favorite location item swipe
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    adapter.removeItem(position)
                    favoriteList.removeAt(position)
                    updateSharedPrefs(favoriteList)
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvFavorites)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    val latLng = place.latLng
                    val latitude = latLng?.latitude.toString()
                    val longitude = latLng?.longitude.toString()
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
                if (response.body() != null) {
                    val favoriteItem = FavoriteLocation(response.body()!!, city)
                    favoriteList.add(favoriteItem)
                    updateSharedPrefs(favoriteList)
                    adapter.updateList(favoriteList)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("Tag", "error")
            }
        })
    }

    private fun updateSharedPrefs(favoriteList: MutableList<FavoriteLocation>) {
        val json = Gson().toJson(favoriteList)
        val editor = sharedPreferences.edit()
        editor.putString("favoriteList", json)
        editor.apply()
    }

    private fun getFavoriteListFromPrefs(): MutableList<FavoriteLocation> {
        val json = sharedPreferences.getString("favoriteList", Gson().toJson(defaultList))
        val type = object : TypeToken<List<FavoriteLocation>>() {}.type
        return Gson().fromJson<List<FavoriteLocation>>(json, type).toMutableList()
    }

    private fun getDefaultLocationForecast() {
        val linzLatitude = "48.30693999999999"
        val linzLongitude = "14.28583"
        service.getForecast(linzLatitude, linzLongitude).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.body() != null) {
                    splashDialog.dismiss()
                    defaultList.add(FavoriteLocation(response.body()!!, "Linz"))
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("Tag", "error")
            }
        })
    }
}
