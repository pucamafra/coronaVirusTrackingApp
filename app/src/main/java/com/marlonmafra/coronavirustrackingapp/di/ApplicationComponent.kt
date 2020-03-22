package com.marlonmafra.coronavirustrackingapp.di

import com.marlonmafra.coronavirustrackingapp.feafures.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}