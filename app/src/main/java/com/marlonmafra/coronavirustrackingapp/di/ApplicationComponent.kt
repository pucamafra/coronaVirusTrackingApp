package com.marlonmafra.coronavirustrackingapp.di

import com.marlonmafra.coronavirustrackingapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}