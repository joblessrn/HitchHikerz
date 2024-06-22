package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import java.time.LocalDate

data class TripToPost(
    val fromCity:String,
    val fromGeoTag:String,
    val toCity:String,
    val toGeoTag:String,
    val date: LocalDate,
    val seats:Int,
    val takenSeats:Int = 0,
    val twoPassengersBack:Boolean,
    val autoBrand:String,
    val autoModel:String,
    val animalsAllowed:Boolean,
    val dopInfo:String,
    val driverID:String
)
