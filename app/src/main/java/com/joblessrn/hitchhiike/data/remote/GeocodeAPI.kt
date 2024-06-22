package com.joblessrn.hitchhiike.data.remote

import com.joblessrn.hitchhiike.Utility
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeAPI {
    @GET("1.x")
    suspend fun getCityCoordinates(
        @Query("apikey") apikey:String = Utility.geocoder_key,
        @Query("geocode") cityName: String,
        @Query("format") format: String = "json"
    ):String
}

interface SuggestsAPI {
    @GET("suggest")
    suspend fun getSuggests(
        @Query("text") place: String,
        @Query("types") types:String = "locality",
        @Query("results") results:Int = 3,
        @Query("apikey") apikey:String = Utility.suggests_key
    ): Suggests
}