package com.joblessrn.hitchhiike.presentation.new_trip.as_passenger

import java.time.LocalDate

data class TripToFind(
    val from:String,
    val to:String,
    val date:LocalDate,
    val seats:Int
)
