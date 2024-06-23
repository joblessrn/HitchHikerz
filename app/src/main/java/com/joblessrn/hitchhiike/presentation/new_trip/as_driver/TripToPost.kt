package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import java.time.LocalDate

data class TripToPost(
    val fromCity:String = "",
    val fromGeoTag:String = "",
    val toCity:String = "",
    val toGeoTag:String = "",
    val date: LocalDate = LocalDate.now(),
    val seats:Int = 0,
    val takenSeats:Int = 0,
    val twoPassengersBack:Boolean = true,
    val autoBrand:String = "",
    val autoModel:String = "",
    val animalsAllowed:Boolean = false,
    val dopInfo:String = "",
    val driverID:String = ""
)
