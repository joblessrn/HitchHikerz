package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import com.joblessrn.hitchhiike.data.remote.Coordinate
import java.time.LocalDate

data class TripToPost(
    var fromCountry:String = "",
    var fromCity:String = "",
    var fromAddress:String? = "",
    var fromGeoTag: Coordinate? = null,
    var toCountry:String = "",
    var toCity:String = "",
    var toAddress:String? = "",
    var toGeoTag:Coordinate? = null,
    var date: LocalDate = LocalDate.now(),
    var time:String = "12:00",
    var seats:Int = 1,
    var takenSeats:Int = 0,
    var twoPassengersBack:Boolean = false,
    var autoBrand:String = "",
    //val autoModel:String = "",
    var animalsAllowed:Boolean = false,
    var description:String = "",
    var driverID:String = "zzzzzzzzzzz"
)

