package com.marlonmafra.coronavirustrackingapp.data

import com.marlonmafra.coronavirustrackingapp.network.TimelineResponse
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.Single

interface CoronaTrackingDataSource {

    fun getTrackingLocation(): Single<TrackingResponse>

    fun getTimeline(id: Int): Single<TimelineResponse>
}