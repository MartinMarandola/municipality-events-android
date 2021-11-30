package com.ar.municipalityevents.view

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