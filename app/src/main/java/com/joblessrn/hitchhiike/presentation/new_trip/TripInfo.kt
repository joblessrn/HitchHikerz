package com.joblessrn.hitchhiike.presentation.new_trip

data class TripInfo(
    val from:String,
    val to:String,
    val price:Int,
    val departTime:String,
    val arriveTime:String,
    val driverName:String,
    val driverRating:Float,
    val seats:String
)
