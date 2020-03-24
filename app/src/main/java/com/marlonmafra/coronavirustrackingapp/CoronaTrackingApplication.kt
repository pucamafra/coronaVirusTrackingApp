package com.marlonmafra.coronavirustrackingapp

import android.app.Application
import com.blongho.country_data.World
import com.google.android.gms.ads.MobileAds
import com.marlonmafra.coronavirustrackingapp.di.ApplicationComponent
import com.marlonmafra.coronavirustrackingapp.di.DaggerApplicationComponent
import com.marlonmafra.coronavirustrackingapp.di.DataModule
import com.marlonmafra.coronavirustrackingapp.di.NetworkModule

class CoronaTrackingApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        World.init(applicationContext)
        MobileAds.initialize(this)
    }

    private fun setupDagger() {
        appComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(getString(R.string.base_url)))
            .dataModule(DataModule())
            .build()
    }
}