package com.marlonmafra.coronavirustrackingapp.di

import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingRepository
import com.marlonmafra.coronavirustrackingapp.network.TrackingApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideTrackingDataSource(trackingApiService: TrackingApiService): CoronaTrackingDataSource {
        return CoronaTrackingRepository(trackingApiService)
    }
}