package com.marlonmafra.coronavirustrackingapp.data.remote

import com.marlonmafra.coronavirustrackingapp.network.TimelineResponse
import com.marlonmafra.coronavirustrackingapp.network.TrackingApiService
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.Single
import javax.inject.Inject

class RemoteTrackingData @Inject constructor(
    private val trackingApiService: TrackingApiService
) {

    fun getTrackingLocation(): Single<TrackingResponse> = trackingApiService.getTrackingLocations()

    fun getTimeline(id: Int): Single<TimelineResponse> = trackingApiService.getTimeline(id)
}