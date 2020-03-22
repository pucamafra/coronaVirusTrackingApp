package com.marlonmafra.coronavirustrackingapp.data

import com.marlonmafra.coronavirustrackingapp.network.TrackingApiService
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.Single

class CoronaTrackingRepository(
    private val trackingApiService: TrackingApiService
) : CoronaTrackingDataSource {

    override fun getTrackingLocation(): Single<TrackingResponse> =
        trackingApiService.getTrackingLocations()
            .doOnSuccess {
                println()
            }
            .doOnError {
                println()
            }
}