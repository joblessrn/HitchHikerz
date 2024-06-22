package com.joblessrn.hitchhiike

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPS_APIKEY)
    }
}