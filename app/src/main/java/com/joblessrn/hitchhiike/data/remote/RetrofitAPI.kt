package com.joblessrn.hitchhiike.data.remote

import com.joblessrn.hitchhiike.Utility
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import com.yandex.mapkit.geometry.Point
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeAPI {
    @GET("1.x")
    suspend fun getCityCoordinates(
        @Query("apikey") apikey:String = Utility.geocoder_key,
        @Query("geocode") place: String,
        @Query("format") format: String = "json"
    ):Coordinate?
}

interface SuggestsAPI {
    @GET("suggest")
    suspend fun getCitySuggests(
        @Query("text") place: String,
        @Query("types") types:String = "locality",
        @Query("results") results:Int = 7,
        @Query("print_address") printAddress:Int = 1,
        @Query("apikey") apikey:String = Utility.suggests_key
    ): Suggests
    @GET("suggest")
    suspend fun getAddressSuggests(
        @Query("text") place: String,
        @Query("types") types:String = "house,biz,metro",
        @Query("results") results:Int = 7,
        @Query("print_address") printAddress:Int = 1,
        @Query("apikey") apikey:String = Utility.suggests_key
    ): Suggests
}