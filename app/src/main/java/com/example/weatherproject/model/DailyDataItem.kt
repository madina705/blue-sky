import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyDataItem(

    @field:SerializedName("windGust")
    val windGust: Double? = null,

    @field:SerializedName("apparentTemperatureMinTime")
    val apparentTemperatureMinTime: Int? = null,

    @field:SerializedName("temperatureMax")
    val temperatureMax: Double? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("precipIntensityMax")
    val precipIntensityMax: Double? = null,

    @field:SerializedName("windBearing")
    val windBearing: Int? = null,

    @field:SerializedName("ozone")
    val ozone: Double? = null,

    @field:SerializedName("temperatureMaxTime")
    val temperatureMaxTime: Int? = null,

    @field:SerializedName("apparentTemperatureMin")
    val apparentTemperatureMin: Double? = null,

    @field:SerializedName("sunsetTime")
    val sunsetTime: Int? = null,

    @field:SerializedName("temperatureLow")
    val temperatureLow: Double? = null,

    @field:SerializedName("precipType")
    val precipType: String? = null,

    @field:SerializedName("humidity")
    val humidity: Double? = null,

    @field:SerializedName("moonPhase")
    val moonPhase: Double? = null,

    @field:SerializedName("windSpeed")
    val windSpeed: Double? = null,

    @field:SerializedName("apparentTemperatureLowTime")
    val apparentTemperatureLowTime: Int? = null,

    @field:SerializedName("sunriseTime")
    val sunriseTime: Int? = null,

    @field:SerializedName("apparentTemperatureLow")
    val apparentTemperatureLow: Double? = null,

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("precipProbability")
    val precipProbability: Double? = null,

    @field:SerializedName("temperatureHighTime")
    val temperatureHighTime: Int? = null,

    @field:SerializedName("visibility")
    val visibility: Double? = null,

    @field:SerializedName("precipIntensity")
    val precipIntensity: Double? = null,

    @field:SerializedName("cloudCover")
    val cloudCover: Double? = null,

    @field:SerializedName("temperatureMin")
    val temperatureMin: Double? = null,

    @field:SerializedName("apparentTemperatureHighTime")
    val apparentTemperatureHighTime: Int? = null,

    @field:SerializedName("pressure")
    val pressure: Double? = null,

    @field:SerializedName("dewPoint")
    val dewPoint: Double? = null,

    @field:SerializedName("temperatureMinTime")
    val temperatureMinTime: Int? = null,

    @field:SerializedName("uvIndexTime")
    val uvIndexTime: Int? = null,

    @field:SerializedName("apparentTemperatureMax")
    val apparentTemperatureMax: Double? = null,

    @field:SerializedName("temperatureHigh")
    val temperatureHigh: Double? = null,

    @field:SerializedName("temperatureLowTime")
    val temperatureLowTime: Int? = null,

    @field:SerializedName("apparentTemperatureHigh")
    val apparentTemperatureHigh: Double? = null,

    @field:SerializedName("time")
    val time: Long? = null,

    @field:SerializedName("precipIntensityMaxTime")
    val precipIntensityMaxTime: Int? = null,

    @field:SerializedName("windGustTime")
    val windGustTime: Int? = null,

    @field:SerializedName("uvIndex")
    val uvIndex: Int? = null,

    @field:SerializedName("apparentTemperatureMaxTime")
    val apparentTemperatureMaxTime: Int? = null
) : Serializable