package com.joblessrn.hitchhiike.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.yandex.mapkit.geometry.Point
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitInstanceGeocoder {

    val gson  = GsonBuilder()
        .registerTypeAdapter(Coordinate::class.java,GeocodeDeserializer())
        .create()

    val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GeocodeAPI::class.java)
    }
}
class GeocodeDeserializer : JsonDeserializer<Coordinate?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Coordinate? {
        val responseData = json?.asJsonObject?.getAsJsonObject("response")
            ?.getAsJsonObject("GeoObjectCollection")
            ?.getAsJsonArray("featureMember")
        if (responseData != null && responseData.size() > 0) {
            val point = responseData[0].asJsonObject
                .getAsJsonObject("GeoObject")
                .getAsJsonObject("Point")
                .get("pos").asString
            return stringToCoordinate(point)
        }
        return null
    }

    private fun stringToCoordinate(point:String):Coordinate{
        val parts = point.split(" ", limit = 2)
        return Coordinate(parts[1].toDouble(),parts[0].toDouble())
    }
}
data class Coordinate(
    val latitude:Double,
    val longitude:Double
){
    fun toPoint():Point = Point(latitude,longitude)
}
