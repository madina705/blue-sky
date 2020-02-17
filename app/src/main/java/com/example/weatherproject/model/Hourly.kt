import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hourly(

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("data")
    val data: List<HourlyDataItem?>? = null,

    @field:SerializedName("icon")
    val icon: String? = null

) : Serializable