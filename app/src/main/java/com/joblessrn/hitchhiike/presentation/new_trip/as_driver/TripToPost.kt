package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import com.yandex.mapkit.geometry.Point
import java.time.LocalDate

data class TripToPost(
    val fromCountry:String = "",
    val fromRegion:String = "",
    val fromCity:String = "",
    val fromAddress:String = "",
    val fromGeoTag: Point? = null,
    val toCountry:String = "",
    val toRegion:String = "",
    val toCity:String = "",
    val toAddress:String = "",
    val toGeoTag:String = "",
    val date: LocalDate = LocalDate.now(),
    val seats:Int = 0,
    val takenSeats:Int = 0,
    val twoPassengersBack:Boolean = false,
    val autoBrand:String = "",
    val autoModel:String = "",
    val animalsAllowed:Boolean = false,
    val dopInfo:String = "",
    val driverID:String = ""
)
