package com.marlonmafra.coronavirustrackingapp.data

import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.Single

interface CoronaTrackingDataSource {

    fun getTrackingLocation(): Single<TrackingResponse>
}