import com.google.gson.annotations.SerializedName

data class Flags(

    @field:SerializedName("nearest-station")
    val nearestStation: Double? = null,

    @field:SerializedName("sources")
    val sources: List<String?>? = null,

    @field:SerializedName("units")
    val units: String? = null,

    @field:SerializedName("meteoalarm-license")
    val meteoalarmLicense: String? = null
)