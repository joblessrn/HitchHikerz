package com.joblessrn.hitchhiike.data.remote

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitInstance {
    val gson  = GsonBuilder()
        .registerTypeAdapter(String::class.java,MyDeserializer2())
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://geocode-maps.yandex.ru/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GeocodeAPI::class.java)
}
class MyDeserializer2 : JsonDeserializer<String> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        val responseData = json?.asJsonObject?.getAsJsonObject("response")
            ?.getAsJsonObject("GeoObjectCollection")
            ?.getAsJsonArray("featureMember")
        if (responseData != null && responseData.size() > 0) {
            val point = responseData[0].asJsonObject
                .getAsJsonObject("GeoObject")
                .getAsJsonObject("Point")
                .get("pos").asString
            Log.d("Tagger","poin1 = ${point}")
            return reversePoint(point)

        }
        return "Что то не так"
    }

    fun reversePoint(point:String):String{
        val parts = point.split(" ", limit = 2)
        return "${parts[1]} ${parts[0]}"
    }
}

