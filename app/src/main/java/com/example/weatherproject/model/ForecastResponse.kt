import com.google.gson.annotations.SerializedName

data class ForecastResponse(

    @field:SerializedName("currently")
    val currently: Currently? = null,

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("currentCity")
    val timezone: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("daily")
    val daily: Daily? = null,

    @field:SerializedName("flags")
    val flags: Flags? = null,

    @field:SerializedName("hourly")
    val hourly: Hourly? = null,

    @field:SerializedName("minutely")
    val minutely: Minutely? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null

)