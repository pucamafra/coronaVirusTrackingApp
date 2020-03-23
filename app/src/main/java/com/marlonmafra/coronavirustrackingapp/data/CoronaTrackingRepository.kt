package com.marlonmafra.coronavirustrackingapp.data

import com.marlonmafra.coronavirustrackingapp.data.local.LocalTrackingData
import com.marlonmafra.coronavirustrackingapp.data.remote.RemoteTrackingData
import com.marlonmafra.coronavirustrackingapp.model.Location
import com.marlonmafra.coronavirustrackingapp.network.TimelineResponse
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.Single

class CoronaTrackingRepository(
    private val remoteTrackingData: RemoteTrackingData,
    private val localTrackingData: LocalTrackingData
) : CoronaTrackingDataSource {

    override fun getTrackingLocation(): Single<TrackingResponse> =
        remoteTrackingData.getTrackingLocation()

    override fun getTimeline(id: Int): Single<TimelineResponse> = remoteTrackingData.getTimeline(id)
}