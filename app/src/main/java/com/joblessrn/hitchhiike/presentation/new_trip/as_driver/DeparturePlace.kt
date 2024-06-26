package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import com.yandex.mapkit.geometry.Point

data class DeparturePlace(
    val country:String = "Россия",
    val region:String = "",
    val city:String = "",
    val streetHouse: StreetHouse? = null,
    val metroStation:String? = null,
    val poi:String? = null,   //point of interest
    val geoTag: Point? = null,
)

data class StreetHouse(
    val street: String,
    val house:Int
)
