package com.joblessrn.hitchhiike.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
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
        val filteredResults = json
            ?.asJsonObject
            ?.getAsJsonArray("results")
            ?.mapNotNull { resultElement ->
                val resultObject = resultElement.asJsonObject
                //val tagsArray = resultObject.getAsJsonArray("tags")
                //if (tagsArray?.contains(JsonPrimitive("locality")) == true) {
                    resultObject.getAsJsonObject("title").get("text").asString
                //} else {
                    //null
                //}
            }
        return Suggests(filteredResults ?: emptyList())
    }
}