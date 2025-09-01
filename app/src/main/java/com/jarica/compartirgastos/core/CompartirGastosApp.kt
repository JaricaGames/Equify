package com.jarica.compartirgastos.core

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CompartirGastosApp : Application(){

    //INICIAMOS EL SDK DE GOOGLE ADMOB
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}




