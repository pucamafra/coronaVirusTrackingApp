package com.marlonmafra.coronavirustrackingapp

import android.app.Application
import com.marlonmafra.coronavirustrackingapp.di.ApplicationComponent
import com.marlonmafra.coronavirustrackingapp.di.DaggerApplicationComponent
import com.marlonmafra.coronavirustrackingapp.di.NetworkModule

class CoronaTrackingApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        appComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule("https://coronavirus-tracker-api.herokuapp.com/v2/"))
            .build()
    }
}