package com.marlonmafra.coronavirustrackingapp.network

import io.reactivex.Single
import retrofit2.http.GET

interface TrackingApiService {

    @GET("locations?timelines=1")
    fun getTrackingLocations(): Single<TrackingResponse>
}