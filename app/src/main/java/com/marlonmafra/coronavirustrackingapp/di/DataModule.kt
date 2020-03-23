package com.marlonmafra.coronavirustrackingapp.di

import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingRepository
import com.marlonmafra.coronavirustrackingapp.data.local.LocalTrackingData
import com.marlonmafra.coronavirustrackingapp.data.remote.RemoteTrackingData
import com.marlonmafra.coronavirustrackingapp.network.TrackingApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideTrackingDataSource(
        localTrackingData: LocalTrackingData,
        remoteTrackingData: RemoteTrackingData
    ): CoronaTrackingDataSource = CoronaTrackingRepository(remoteTrackingData, localTrackingData)
}