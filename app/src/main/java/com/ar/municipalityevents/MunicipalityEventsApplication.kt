package com.ar.municipalityevents

import android.app.Application

class MunicipalityEventsApplication : Application() {

    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}