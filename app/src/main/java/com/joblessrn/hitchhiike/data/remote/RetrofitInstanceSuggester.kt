package com.joblessrn.hitchhiike.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.joblessrn.hitchhiike.data.remote.models.Suggest
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitInstanceSuggester {
    val gson = GsonBuilder()
        .registerTypeAdapter(Suggests::class.java, SuggestsDeserializer())
        .create()

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://suggest-maps.yandex.ru/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SuggestsAPI::class.java)
    }
}

class SuggestsDeserializer : JsonDeserializer<Suggests> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Suggests {
        val jsonObject = json?.asJsonObject
        val results = jsonObject?.getAsJsonArray("results")

        val suggests = mutableListOf<Suggest>()

        if (results != null) {
            for (result in results) {
                val resultObject = result.asJsonObject

                val title = resultObject.getAsJsonObject("title").get("text").asString

                val addressComponents = resultObject?.getAsJsonObject("address")?.getAsJsonArray("component")

                var country = ""
                var region = ""
                //var street = ""
                //var house = ""

                if (addressComponents != null) {
                    for (component in addressComponents) {
                        val componentObject = component.asJsonObject
                        val kindArray = componentObject.getAsJsonArray("kind")

                        for (kind in kindArray) {
                            when (kind.asString) {
                                "COUNTRY" -> country = componentObject.get("name").asString
                                "PROVINCE" -> if (region.isBlank()) region = componentObject.get("name").asString
                                "AREA" -> if (region.isBlank()) region = componentObject.get("name").asString
                                //"STREET" -> street = componentObject.get("name").asString
                                //"HOUSE" -> house = componentObject.get("name").asString
                            }
                        }
                    }
                }
                suggests.add(Suggest(country = country,
                    region = region,
                    place = title,
                    /*address = "$street $house"*/))
            }
        }
        return Suggests(suggests)
    }
}