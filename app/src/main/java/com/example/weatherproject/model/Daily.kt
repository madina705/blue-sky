

import com.google.gson.annotations.SerializedName

data class Daily(

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("data")
	val data: List<DailyDataItem?>? = null,

	@field:SerializedName("icon")
	val icon: String? = null

)