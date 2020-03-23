package com.marlonmafra.coronavirustrackingapp.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackingApiService {

    @GET("locations")
    fun getTrackingLocations(): Single<TrackingResponse>

    @GET("locations/{id}?timelines=1")
    fun getTimeline(@Path("id") id: Int): Single<TimelineResponse>
}