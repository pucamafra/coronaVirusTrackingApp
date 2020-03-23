package com.marlonmafra.coronavirustrackingapp.di

import com.marlonmafra.coronavirustrackingapp.features.details.CountryDetails
import com.marlonmafra.coronavirustrackingapp.features.home.MainActivity
import com.marlonmafra.coronavirustrackingapp.features.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(mainActivity: SplashActivity)

    fun inject(countryDetails: CountryDetails)
}