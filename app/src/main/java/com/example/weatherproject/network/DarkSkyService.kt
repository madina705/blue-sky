import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DarkSkyService {

    companion object {
        operator fun invoke(context: Context): DarkSkyService {
            return Retrofit.Builder()
                .baseUrl("https://api.darksky.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DarkSkyService::class.java)
        }
    }

    @GET("/forecast/fd51416c5c0a4c18e39490cdcefc641b/{latitude},{longitude}?units=si")
    fun getForecast(@Path("latitude") latitude: String, @Path("longitude") longitude: String): Call<ForecastResponse>

}