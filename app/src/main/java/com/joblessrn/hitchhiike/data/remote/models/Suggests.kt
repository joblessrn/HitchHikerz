package com.joblessrn.hitchhiike.data.remote.models

data class Suggests(
    val hints: List<Suggest>
)
data class Suggest(
    val country:String = "",
    val region:String = "",
    val place:String = "",
)