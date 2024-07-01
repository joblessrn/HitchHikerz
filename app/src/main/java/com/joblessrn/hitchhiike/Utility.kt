package com.joblessrn.hitchhiike
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.reflect.full.memberProperties
object Utility {
    val geocoder_key = BuildConfig.GEOCODER_APIKEY
    val suggests_key = BuildConfig.GEOSUGGEST_APIKEY
}

fun <T : Any> checkEmptyFields(data: T): Boolean {
    val properties = data::class.memberProperties

    for (property in properties) {
        val value = property.getter.call(data)
        if (value == null || (value is String && value.isBlank())) {
            return false
        }
    }
    return true
}

fun getCurrentTime(format: SimpleDateFormat): String {
    return format.format(Calendar.getInstance().time)
}